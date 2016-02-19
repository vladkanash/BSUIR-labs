#include <stdio.h>
#include <stdlib.h>
#include <time.h>


#define N 1300
#define MAX_RAND 500

float** alloc_matrix(unsigned size) {
    float** ptr = NULL;

    ptr = (float**)malloc(sizeof(float*) * size);

    for (int i = 0; i < size; i++) {
        ptr[i] = (float*)malloc(sizeof(float) * size);
    }

    return ptr;
}

void rand_matrix(float** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
       for (int j = 0; j < size; j++) {
           ptr[i][j] = (rand() % (MAX_RAND + 1)) / (float)(rand() % MAX_RAND + 1);
       }
    }
}

void print_matrix(float** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            printf("%4.2f ", ptr[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void clean_matrix(float** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
        free(ptr[i]);
    }
    free(ptr);
}

float** multiply_matrix(float** ptr_1, float** ptr_2, unsigned size) {
    float** result = alloc_matrix(size);

    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[i][k] += ptr_1[i][j] * ptr_2[j][k];
            }
        }
    }

    return result;
}

long time_diff(clock_t t1, clock_t t2) {
    long elapsed;
    elapsed = (long) (((double)t2 - t1) / CLOCKS_PER_SEC * 1000);
    return elapsed;
}

float main() {
    srand((unsigned int) time(NULL));
    clock_t t1, t2;

    float** mx1 = alloc_matrix(N);
    float** mx2 = alloc_matrix(N);

    rand_matrix(mx1, N);
    rand_matrix(mx2, N);

    t1 = clock();
    float** mx_res = multiply_matrix(mx1, mx2, N);
    t2 = clock();

//    print_matrix(mx1, N);
//    print_matrix(mx2, N);
//
//    printf("===================RESULT==========================\n");
//    print_matrix(mx_res, N);

    printf("Time elapsed: %ld ms\n", time_diff(t1, t2));

    clean_matrix(mx1, N);
    clean_matrix(mx2, N);
    clean_matrix(mx_res, N);
    return 0;
}