#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string.h>
#include <sys/time.h>

#include <cuda_runtime.h>
#include <device_launch_parameters.h>

#define FIBER 32
#define N 1024
#define DATA_SIZE N * N * sizeof(int)

void print(int** r);

__global__ void kernel_shared(int *a, int *c, int *b, int *r) {
    __shared__ int smem[FIBER * FIBER];

    int bx = blockIdx.x;
    int by = blockIdx.y;
    int tx = threadIdx.x;
    int ty = threadIdx.y;
    int dx = blockDim.x;
    int dy = blockDim.y;

    int block_x = bx * dx;
    int block_y = by * dy;

    int x = block_x + tx;
    int y = block_y + ty;
    int idx = x + y * N;

    int smem_x = by * dy + tx;
    int smem_y = bx * dx + ty;
    int smem_idx = smem_x + smem_y * N;

    smem[dx * ty + tx] = a[smem_idx] + a[smem_idx];

    __syncthreads();

	r[idx] = smem[dy * tx + ty] * b[idx] - c[idx];
}

__global__ void kernel(int *a, int *x, int *b, int *r) {
    int bx = blockIdx.x;
    int by = blockIdx.y;
    int tx = threadIdx.x;
    int ty = threadIdx.y;
    int dx = blockDim.x;
    int dy = blockDim.y;

    int i = bx * dx + tx;
    int j = by * dy + ty;
    r[i+ j * N] = (a[j + i * N] + a[j + i * N]) * b[i + j * N] - x[i + j * N];
}

using namespace std;

int* processMtrx(int* A, int* B, int* C) {
    int *R = (int*)aligned_alloc(32, DATA_SIZE);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            R[i * N + j] = (A[j * N + i] + A[j * N + i]) * B[i * N + j] - C[i * N + j];
        }
    }
    return R;
}

void myCudaMalloc(int **ptr) {
    cudaError_t error = cudaMalloc((void**) ptr, DATA_SIZE);
    if (error != cudaSuccess) {
		printf("%s\n", cudaGetErrorString(error));
	}
}

void cudaMemcpyHost2Device(int *src, int *dst) {
    cudaError_t error = cudaMemcpy(dst, src, DATA_SIZE, cudaMemcpyHostToDevice);
    if (error != cudaSuccess) {
		printf("%s\n", cudaGetErrorString(error));
	}
}

void cudaMemcpyDevice2Host(int *src, int *dst) {
    cudaError_t error = cudaMemcpy(src, dst, DATA_SIZE, cudaMemcpyDeviceToHost);
    if (error != cudaSuccess) {
		printf("%s\n", cudaGetErrorString(error));
	}
}

bool checkForErrors(int *ptr1, int *ptr2, int *ptr3) {
    for (int i = 0; i < N; i++)
		for (int j = 0; j < N; j++)
			if((ptr1[i * N + j] != ptr3[i * N + j]) || (ptr1[i * N + j] != ptr2[i * N + j])) {
                printf("\n%d != %d [%d]\n", ptr1[i * N + j], ptr2[i * N + j], i * N + j);
                return false;
            }

    return true;
}

int* randMtrx(int *MATR)
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
        {
            MATR[i * N + j] = rand() % 3;
        }
    }

    return MATR;
}

void print(int* r)
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
        {
            printf("%d\t", r[i * N + j]);
            if (j == N - 1)
            {
                printf("\n");
            }
        }
    }
    printf("\n");
    printf("\n");
}

int* processCPU(int *A, int *B, int *X) {
    int * R ;

    randMtrx(A);
    randMtrx(X);
    randMtrx(B);

//    printf("original =============\n");
//    print(A);
//    print(B);
//    printf("==================================\n");

    struct timeval stopm, startm;
    gettimeofday(&startm, NULL);

    R = processMtrx(A, B, X);

    gettimeofday(&stopm, NULL);

    printf("runTimeCPU =  %f \n", (float)(stopm.tv_usec - startm.tv_usec) / 1000);
    return R;
}

int* processGPU(int *A, int *B, int *X, bool shared) {
    int* R;
    int *Res = (int*)aligned_alloc(32, DATA_SIZE);
    memset(Res, 0, DATA_SIZE);

    myCudaMalloc(&R);
    cudaMemcpyHost2Device(Res, R);

    cudaEvent_t start, stop;

    cudaEventCreate(&start);
    cudaEventCreate(&stop);
    cudaEventRecord(start);

    dim3 threads(FIBER, FIBER);
    dim3 blocks((N + (FIBER - 1)) / FIBER, (N + ( FIBER - 1)) / FIBER);

    cudaEventSynchronize(start);

    if (shared) {
        kernel_shared <<< blocks, threads >>> (A, X, B, R);
    } else {
        kernel <<< blocks, threads >>> (A, X, B, R);
    }

    cudaError_t error = cudaGetLastError();
    if (error != cudaSuccess) {
        printf("%s\n", cudaGetErrorString(error));
    }

    cudaEventRecord(stop);
    cudaEventSynchronize(stop);

    float timer = 0;

    cudaEventElapsedTime(&timer, start, stop);
    if (shared) {
        cout << "runTimeGPU shared = " << timer << endl;
    } else {
        cout << "runTimeGPU = " << timer << endl;
    }
    cudaEventRecord(start);

    cudaMemcpyDevice2Host(Res, R);
    return Res;
}

int main(int argc, char* argv[])
{
    int *dev_A_shared, *dev_B_shared, *dev_C_shared;
    int *dev_A, *dev_B, *dev_C;
    int *A, *B, *C;
    int *cpu_result, *gpu_result, *gpu_result_shared;

	A = (int*)aligned_alloc(32, DATA_SIZE);
	B = (int*)aligned_alloc(32, DATA_SIZE);
	C = (int*)aligned_alloc(32, DATA_SIZE);

    cpu_result = processCPU(A, B, C);

    myCudaMalloc(&dev_A_shared);
    myCudaMalloc(&dev_B_shared);
    myCudaMalloc(&dev_C_shared);

    myCudaMalloc(&dev_A);
    myCudaMalloc(&dev_B);
    myCudaMalloc(&dev_C);


    cudaMemcpyHost2Device(A, dev_A_shared);
    cudaMemcpyHost2Device(B, dev_B_shared);
    cudaMemcpyHost2Device(C, dev_C_shared);

    cudaMemcpyHost2Device(A, dev_A);
    cudaMemcpyHost2Device(B, dev_B);
    cudaMemcpyHost2Device(C, dev_C);


    gpu_result = processGPU(dev_A, dev_B, dev_C, false);
    gpu_result_shared = processGPU(dev_A_shared, dev_B_shared, dev_C_shared, true);

    if (!checkForErrors(cpu_result, gpu_result_shared, gpu_result)) {
        printf("Errors occured!\n");
    } else {
        printf("No errors!\n");
    }


//    print(gpu_result_shared);
//    printf("\n\n");
//    print(cpu_result);

}

