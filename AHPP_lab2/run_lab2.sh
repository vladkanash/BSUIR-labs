#!/bin/bash

echo -n "Auto vectorization without using OpenMP: "
echo `./Debug/lab2_autovect_no_openmp`

echo -n "Auto vectorization with OpenMP: " 
echo `./Debug/lab2_autovect_openmp`

echo -n "Intrinsic vectorization without using OpenMP: "
echo `./Debug/lab2_customvect_no_openmp`

echo -n "Intrinsic vectorization with OpenMP: "
echo `./Debug/lab2_customvect_openmp`


