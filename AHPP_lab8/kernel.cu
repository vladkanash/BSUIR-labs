//#include <iostream>
//#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
//#include <stdio.h>
//#include <stdlib.h>
//
//#define FIBER 32
//
//using namespace std;
//using namespace cv;
//
//void cudaProcess(const Mat &image, Point &size);
//void cudaInitMem(const Mat &image, uchar *outputImg, Point size);
//
//__global__ void kernel(Point* size, unsigned* data) {
//
//    int bx = blockIdx.x;
//    int by = blockIdx.y;
//    int tx = threadIdx.x;
//    int ty = threadIdx.y;
//    int dx = blockDim.x;
//    int dy = blockDim.y;
//
//    int block_x = bx * dx;
//    int block_y = by * dy;
//
//    int i = block_x + tx;
//    int j = block_y + ty;
//
//    int X = size->x;
//    int Y = size->y;
//
//    int idx = j * X + i;
//    int idx_reverse = (Y - 1 - j) * X + (X - 1 - i);
//
//    __shared__ unsigned smem[(FIBER*3)*FIBER];
//
//    const unsigned inputWidth = size->x / sizeof(int);
//
//    const int baseX = (blockIdx.x*blockDim.x+threadIdx.x)*3;
//    const int baseY = blockIdx.y * blockDim.y + threadIdx.y;
//
////    smem[threadIdx.y * FIBER * 3 + threadIdx.x] = data[baseY * inputWidth + baseX];
////    smem[threadIdx.y * FIBER * 3 + threadIdx.x + blockDim.x] = data[baseY * inputWidth + baseX + blockDim.x];
////    smem[threadIdx.y * FIBER * 3 + threadIdx.x + blockDim.x*2] = data[baseY * inputWidth+baseX + blockDim.x * 2];
////
////    __syncthreads();
//
////    unsigned buffer[3];
//    idx *= 3;
//    idx_reverse += 3;
//
//    if (j < Y / 2) {
////        buffer[0] = data[idx];
////        buffer[1] = data[idx + 1];
////        buffer[2] = data[idx + 2];
//
//        data[idx] = 0;
//        data[idx + 1] = 180;
//        data[idx + 2] = 0;
//
////        data[idx_reverse] = buffer[0];
////        data[idx_reverse + 1] = buffer[1];
////        data[idx_reverse + 2] = buffer[2];
//    }
//
//
////    else if ((j == Y / 2) && (i <= X / 2) && (Y % 2 != 0)) {
////        idx_reverse = j * X + (X - 1 - i);
////        buffer = data[idx];
////        data[idx] = data[idx_reverse];
////        data[idx_reverse] = buffer;
////    }
//
//
////    uchar buffer = 0;
////
////    if (j < Y / 2) {
////        buffer = data[idx];
////        data[idx] = data[idx_reverse];
////        data[idx_reverse] = buffer;
////    }
////    else if ((j == Y / 2) && (i <= X / 2) && (Y % 2 != 0)) {
////        idx_reverse = j * X + (X - 1 - i);
////        buffer = data[idx];
////        data[idx] = data[idx_reverse];
////        data[idx_reverse] = buffer;
////    }
//}
//
//int main(int argc, char** argv) {
//
//    if(argc != 2) {
//        cout <<" Usage: display_image ImageToLoadAndDisplay" << endl;
//        return -1;
//    }
//
//    Mat image;
//    image = imread(argv[1], CV_LOAD_IMAGE_COLOR);
//
//    if(! image.data ) {
//        cerr <<  "Could not open or find the image" << std::endl ;
//        return -1;
//    }
//
//    Point size = Point_<int>(image.cols, image.rows);
//
//    uchar* output = (uchar*)malloc(image.cols * image.rows * sizeof(char));
//    cudaInitMem(image, output, size);
//
////    cudaProcess(image, size);
//
//
//
//    waitKey(0);
//    return 0;
//}
//
////void cudaProcess(const Mat &image, Point &size) {
////    Point* cuda_size;
////    uchar* cuda_data;
////    size_t data_size = sizeof(uchar) * size.x * size.y;
////
////    cudaError_t error = cudaMalloc((void**) &cuda_data, data_size);
////    if (error != cudaSuccess) {
////        cerr << cudaGetErrorString(error) << endl;
////    }
////
////    error = cudaMemcpy(cuda_data, image.data, data_size, cudaMemcpyHostToDevice);
////    if (error != cudaSuccess) {
////        cerr << cudaGetErrorString(error) << endl;
////    }
////
////    error = cudaMalloc((void**) &cuda_size, sizeof(Point));
////    if (error != cudaSuccess) {
////        cerr << cudaGetErrorString(error) << endl;
////    }
////
////    error = cudaMemcpy(cuda_size, &size, sizeof(Point), cudaMemcpyHostToDevice);
////    if (error != cudaSuccess) {
////        cerr << cudaGetErrorString(error) << endl;
////    }
////
////    dim3 threads(FIBER, FIBER);
////    dim3 blocks((size.x + (FIBER - 1)) / FIBER, (size.y + ( FIBER - 1)) / FIBER);
////    kernel <<< blocks, threads >>> (cuda_size, cuda_data);
////
////    error = cudaMemcpy(image.data, cuda_data, data_size, cudaMemcpyDeviceToHost);
////    if (error != cudaSuccess) {
////        cerr << cudaGetErrorString(error) << endl;
////    }
////}
//
//void cudaInitMem(const Mat &image, uchar* outputImg, Point size) {
//    Point* cuda_size;
//    unsigned *cuda_data = NULL;
//
//    int pitch = 0;
//    int width = size.x * 3;
//    cudaError_t error = cudaMallocPitch((void **)&cuda_data, (size_t *) & pitch, width, size.y);
//    if (error != cudaSuccess) {
//        cerr << cudaGetErrorString(error) << endl;
//    }
//    error = cudaMemcpy2D(cuda_data, pitch, image.data, width, width, size.y, cudaMemcpyHostToDevice);
//    if (error != cudaSuccess) {
//        cerr << cudaGetErrorString(error) << endl;
//    }
//    // можем работать с cuda_data, при этом ширина – pitch байт
//
//    // cudaMemcpy2D(outputImg, width, cuda_data, pitch, width, height, cudaMemcpyDeviceToHost);
//    // в outputImg ширина изображения снова width байт
//
//    error = cudaMalloc((void**) &cuda_size, sizeof(Point));
//    if (error != cudaSuccess) {
//        cerr << cudaGetErrorString(error) << endl;
//    }
//
//    error = cudaMemcpy(cuda_size, &size, sizeof(Point), cudaMemcpyHostToDevice);
//    if (error != cudaSuccess) {
//        cerr << cudaGetErrorString(error) << endl;
//    }
//
//    dim3 threads(FIBER, FIBER);
//    dim3 blocks((width + (FIBER - 1)) / FIBER, (size.y + ( FIBER - 1)) / FIBER);
//    kernel <<< blocks, threads >>> (cuda_size, cuda_data);
//
//
//    error = cudaMemcpy2D(image.data, width, cuda_data, pitch, width, size.y, cudaMemcpyDeviceToHost);
//    if (error != cudaSuccess) {
//        cerr << cudaGetErrorString(error) << endl;
//    }
//
//    namedWindow( "Display window", WINDOW_AUTOSIZE );
//    imshow( "Display window", image );
//}

#include <iostream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <stdio.h>
#include <stdlib.h>

#define FIBER 32

using namespace std;
using namespace cv;

void cudaProcess(const Mat &image, Point &size);

__global__ void kernel(Point* size, uchar* data) {
    __shared__ unsigned smem[(FIBER*3) * FIBER];
    uchar buffer[3];

    int pitch = ( ( size->x * 3 + 127 )  / 128 ) * 128;
    int bx = blockIdx.x;
    int by = blockIdx.y;
    int tx = threadIdx.x;
    int ty = threadIdx.y;
    int dx = blockDim.x;
    int dy = blockDim.y;

    int block_x = bx * dx;
    int block_y = by * dy;

    int i = block_x + tx;
    int j = block_y + ty;

    int X = size->x;
    int Y = size->y;

    int idx = (j * pitch + i * 3);
    int idx_reverse = (Y - 1 - j) * pitch + (X - 1 - i) * 3;

    const int baseX = i * 3;

    smem[ty * dx * 3 + tx] = data[j * pitch / 4 + baseX];
    smem[ty * dx * 3 + tx + dx] = data[j * pitch / 4 + baseX + dx];
    smem[ty * dx * 3 + tx + dx * 2] = data[j * pitch / 4 + baseX + dx * 2];

    __syncthreads();

    if (j < Y / 2 && i <= size->x) {
        buffer[0] = data[idx];
        buffer[1] = data[idx + 1];
        buffer[2] = data[idx + 2];

        data[idx] = data[idx_reverse];
        data[idx + 1] = data[idx_reverse + 1];
        data[idx + 2] = data[idx_reverse + 2];

        data[idx_reverse] = buffer[0];
        data[idx_reverse + 1] = buffer[1];
        data[idx_reverse + 2] = buffer[2];
    }
    if ((j == Y / 2) && (i <= X / 2) && (Y % 2 != 0)) {
        idx_reverse = j * X + (X - 3 - i);
        buffer[0] = data[idx];
        buffer[1] = data[idx + 1];
        buffer[2] = data[idx + 2];

        data[idx] = data[idx_reverse];
        data[idx + 1] = data[idx_reverse + 1];
        data[idx + 2] = data[idx_reverse + 2];

        data[idx_reverse] = buffer[0];
        data[idx_reverse + 1] = buffer[1];
        data[idx_reverse + 2] = buffer[2];
    }
}

int main(int argc, char** argv) {

    if(argc != 2) {
        cout <<" Usage: display_image ImageToLoadAndDisplay" << endl;
        return -1;
    }

    Mat image, imageOld;
    imageOld = imread(argv[1], CV_LOAD_IMAGE_COLOR);
    image = imread(argv[1], CV_LOAD_IMAGE_COLOR);

    if(! image.data ) {
        cerr <<  "Could not open or find the image" << std::endl ;
        return -1;
    }

    Point size = Point_<int>(image.cols, image.rows);

    cudaProcess(image, size);

    namedWindow( "New image", WINDOW_AUTOSIZE );
    imshow( "New image", image );

    namedWindow( "Old image", WINDOW_AUTOSIZE );
    imshow( "Old image", imageOld );

    waitKey(0);
    return 0;
}

void cudaProcess(const Mat &image, Point &size) {
    Point* cuda_size;
    uchar* cuda_data;

    int width = size.x * 3;
    int height = size.y;

    const int pitch = ( ( width + 127 )  / 128 ) * 128;
    cudaMalloc( (void **)&cuda_data, pitch * height );

    for (int i = 0; i < height; i++) {
        cudaMemcpy(
                &cuda_data[i * pitch],
                &image.data[i * width],
                width,
                cudaMemcpyHostToDevice);
    }

    cudaError_t error = cudaMalloc((void**) &cuda_size, sizeof(Point));
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    // t1
    error = cudaMemcpy(cuda_size, &size, sizeof(Point), cudaMemcpyHostToDevice);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    // t2
    dim3 threads(FIBER, FIBER);
    dim3 blocks((pitch + (FIBER - 1)) / FIBER, (size.y + ( FIBER - 1)) / FIBER);
    kernel <<< blocks, threads >>> (cuda_size, cuda_data);
    
    //t3
    error = cudaMemcpy2D(image.data, width, cuda_data, pitch, width, height, cudaMemcpyDeviceToHost);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }
    // t4
}