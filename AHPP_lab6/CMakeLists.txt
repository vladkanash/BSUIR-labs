cmake_minimum_required(VERSION 3.3)
project(AHPP_lab6)

find_package(CUDA)

# Append current NVCC flags by something, eg comput capability
set(CUDA_NVCC_FLAGS ${CUDA_NVCC_FLAGS} --gpu-architecture sm_20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g -Wall -std=c++11")

set(CUDASRCS kernel.cu)

CUDA_ADD_EXECUTABLE(AHPP_lab6 ${CUDASRCS})