cmake_minimum_required(VERSION 3.3)
project(AHPP_lab1)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c11")
set(SOURCE_FILES main.c)


add_executable(AHPP_lab1 ${SOURCE_FILES})
SET_TARGET_PROPERTIES(AHPP_lab1 PROPERTIES COMPILE_FLAGS
        " -O2 -mno-sse2 -mno-avx -fopt-info-vec-optimized")

add_executable(AHPP_lab1_vect ${SOURCE_FILES})
SET_TARGET_PROPERTIES(AHPP_lab1_vect PROPERTIES COMPILE_FLAGS
        "-O2 -msse2 -mavx -ftree-vectorize -fopt-info-vec-optimized")

