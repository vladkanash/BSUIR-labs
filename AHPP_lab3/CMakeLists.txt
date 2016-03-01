cmake_minimum_required(VERSION 3.3)
project(AHPP_lab3)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c11")

set(SOURCE_FILES main.c)
add_executable(AHPP_lab3 ${SOURCE_FILES})