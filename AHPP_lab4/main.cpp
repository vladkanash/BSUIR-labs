#include <iostream>
#include <string.h>
#include <x86intrin.h>

//
// Created by vladkanash on 3/22/16.
//

#define ITER_COUNT 1000000
#define BLOCK_SIZE 1024 * 1024
#define MAX_N 20
#define OFFSET 1024 * 1024
#define ELEMENT_SIZE sizeof(type)

typedef uint64_t type;

int main() {

    type* array;
    unsigned line_size;
    unsigned line_count;
    unsigned offset_count = OFFSET / ELEMENT_SIZE;
    type index;
    uint64_t begin, end;

    for (unsigned N = 1; N <= MAX_N; N++) {

        line_size = BLOCK_SIZE / N;
        line_count = (unsigned int) (line_size / ELEMENT_SIZE);
        size_t size = (size_t) (OFFSET * N);

        array = (type*) aligned_alloc((size_t)(OFFSET), size);
        memset(array, 0, size);

        for (unsigned n = 0; n < N; n++) {
            for (unsigned i = 0; i < line_count; i++) {
                array[n * offset_count + i] = (n == N - 1) ? (type)(i + 1) : (type) ((n + 1) * offset_count + i);
            }
        }

        index = 0;

        begin = __rdtsc();
        for (unsigned long i = 0; i < ITER_COUNT; i++) {
            index = array[index];
            array[index] *= 1;
        }
        end = __rdtsc() - begin;

        printf("N = %2d %llu\r\n", N, end);
    }

    return 0;
}



