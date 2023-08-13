#!/bin/bash

N=${1}

echo "Using N = ${N}"

echo -n "Without optimization: "
echo `./Debug/lab2_no_optimized ${N}`

echo -n "Auto vectorization without using OpenMP: "
echo `./Debug/lab2_autovect_no_openmp ${N}`

echo -n "Auto vectorization with OpenMP: " 
echo `./Debug/lab2_autovect_openmp ${N}`

echo -n "Intrinsic vectorization without using OpenMP: "
echo `./Debug/lab2_customvect_no_openmp ${N}`

echo -n "Intrinsic vectorization with OpenMP: "
echo `./Debug/lab2_customvect_openmp ${N}`


