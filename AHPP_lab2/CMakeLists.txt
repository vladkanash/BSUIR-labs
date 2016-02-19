cmake_minimum_required(VERSION 3.3)
project(AHPP_lab2)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c11 -fopenmp")

set(SOURCE_FILES AHPP_lab2.c)


#Auto vectorizing with OpenMP
add_executable(lab2_autovect_openmp ${SOURCE_FILES})
SET_TARGET_PROPERTIES(lab2_autovect_openmp PROPERTIES COMPILE_FLAGS
        "-O2 -msse2 -mavx -ftree-vectorize -fopt-info-vec-optimized -fopenmp -DAUTO_VECTORIZE_OPENMP")


#Auto vectorizing without OpenMP
add_executable(lab2_autovect_no_openmp ${SOURCE_FILES})
SET_TARGET_PROPERTIES(lab2_autovect_no_openmp PROPERTIES COMPILE_FLAGS
        "-O2 -msse2 -mavx -ftree-vectorize -fopt-info-vec-optimized -DAUTO_VECTORIZE_NO_OPENMP")


#Intrinsic vectorizing without OpenMP
add_executable(lab2_customvect_no_openmp ${SOURCE_FILES})
SET_TARGET_PROPERTIES(lab2_customvect_no_openmp PROPERTIES COMPILE_FLAGS
        "-O2 -msse2 -mavx -DMY_VECTORIZE_NO_OPENMP")


#Intrinsic vectorizing with OpenMP
add_executable(lab2_customvect_openmp ${SOURCE_FILES})
SET_TARGET_PROPERTIES(lab2_customvect_openmp PROPERTIES COMPILE_FLAGS
        "-O2 -msse2 -mavx -DMY_VECTORIZE_OPENMP")



