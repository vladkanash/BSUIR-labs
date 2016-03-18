#include <iostream>
#include <x86intrin.h>

const int BLOCK_SIZE = 32 * 1024 * 8;//32768; //262144;  //3145728
const int CACHE_SIZE = 16 * 1024 * 1024; // 16 Мб
const int OP_COUNT = 25;

template<typename type>
void align(type *buffer, int fragmentSize, int offset, int numberOfWays) {

    for (int i = 0; i < numberOfWays; i++) {

        for (int j = 0; j < fragmentSize; j++) {

            if (i < numberOfWays - 1) {
                buffer[i * offset + j] = offset * (i + 1) + j;
            }
            else {

                if (j < fragmentSize - 1)
                    buffer[i * offset + j] = j + 1;
                else
                    buffer[i * offset + j] = 0;
            }
        }
    }
}


template<typename type>
uint64_t* getResult(type buf[], int offsetInBytes, int maxCountOfIterations, int blockSizeInBytes) {
    uint64_t* results = new uint64_t[OP_COUNT];

    if (!buf)
    exit(EXIT_FAILURE);

    uint64_t begin;
    int offset = (int) (offsetInBytes / sizeof(type));

    for (int i = 1; i < maxCountOfIterations; i++) {

        int fragmentSize = (int) (blockSizeInBytes / (sizeof(int) * i));
        int iterCount = fragmentSize * i;

        align(buf, fragmentSize, offset, i);

        type index;
        index = 0;

        begin = __rdtsc();

        for (int j = 0; j < iterCount; j++) {
            index = buf[index];
            buf[index] *= 1;
            //if(j == 100)
            //printf(" ");
        }

        results[i] = __rdtsc() - begin;
    }

    return results;
}


int main() {

    int number = 4;
    int* buffer;
    uint64_t *results;

    buffer = (int *)aligned_alloc((size_t)(CACHE_SIZE * OP_COUNT), 64);
    results = (uint64_t *)aligned_alloc((size_t)(OP_COUNT * sizeof(uint64_t)), 64);

    for (int j = 1; j < OP_COUNT; j++) {
        results[j] = 0;
    }


    for (int i = 0; i < number; i++) {
        uint64_t *result = getResult(buffer, CACHE_SIZE, OP_COUNT, BLOCK_SIZE);

        for (int j = 1; j < OP_COUNT; j++)
            results[j] += result[j];
    }

    for (int j = 1; j < OP_COUNT; j++)
        results[j] /= (number);

    for (int i = 1; i < OP_COUNT; i++)
        printf("%d %lu\r\n", i, results[i]);

    return 0;
}

