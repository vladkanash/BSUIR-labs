cmake_minimum_required(VERSION 3.3)
project(AHPP_lab8)

find_package(OpenCV REQUIRED)
find_package(CUDA)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
set(CUDA_NVCC_FLAGS ${CUDA_NVCC_FLAGS} --gpu-architecture sm_20)

set(SOURCE_FILES kernel.cu)

CUDA_ADD_EXECUTABLE(AHPP_lab8 ${SOURCE_FILES})
target_link_libraries( AHPP_lab8 ${OpenCV_LIBS} )