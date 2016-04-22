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

    int idx = j * X + i;
    int idx_reverse = (Y - 1 - j) * X + (X - 1 - i);

    uchar buffer = 0;

    if (j < Y / 2) {
        buffer = data[idx];
        data[idx] = data[idx_reverse];
        data[idx_reverse] = buffer;
    }
    else if ((j == Y / 2) && (i <= X / 2) && (Y % 2 != 0)) {
        idx_reverse = j * X + (X - 1 - i);
        buffer = data[idx];
        data[idx] = data[idx_reverse];
        data[idx_reverse] = buffer;
    }
}

int main(int argc, char** argv) {

    if(argc != 2) {
        cout <<" Usage: display_image ImageToLoadAndDisplay" << endl;
        return -1;
    }

    Mat image;
    image = imread(argv[1], CV_LOAD_IMAGE_GRAYSCALE);

    if(! image.data ) {
        cerr <<  "Could not open or find the image" << std::endl ;
        return -1;
    }

    Point size = Point_<int>(image.cols, image.rows);

    cudaProcess(image, size);

    namedWindow( "Display window", WINDOW_AUTOSIZE );
    imshow( "Display window", image );

    waitKey(0);
    return 0;
}

void cudaProcess(const Mat &image, Point &size) {
    Point* cuda_size;
    uchar* cuda_data;
    size_t data_size = sizeof(uchar) * size.x * size.y;

    cudaError_t error = cudaMalloc((void**) &cuda_data, data_size);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    error = cudaMemcpy(cuda_data, image.data, data_size, cudaMemcpyHostToDevice);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    error = cudaMalloc((void**) &cuda_size, sizeof(Point));
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    error = cudaMemcpy(cuda_size, &size, sizeof(Point), cudaMemcpyHostToDevice);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }

    dim3 threads(FIBER, FIBER);
    dim3 blocks((size.x + (FIBER - 1)) / FIBER, (size.y + ( FIBER - 1)) / FIBER);
    kernel <<< blocks, threads >>> (cuda_size, cuda_data);

    error = cudaMemcpy(image.data, cuda_data, data_size, cudaMemcpyDeviceToHost);
    if (error != cudaSuccess) {
        cerr << cudaGetErrorString(error) << endl;
    }
}