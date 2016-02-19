#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>
#include <emmintrin.h>

//#define AUTO_VECTORIZE_OPENMP

#define N 1300
#define MAX_RAND 500
#define TYPE float

TYPE** alloc_matrix(unsigned size) {
    TYPE** ptr = NULL;

    ptr = (TYPE**)malloc(sizeof(TYPE*) * size);

    for (int i = 0; i < size; i++) {
        ptr[i] = (TYPE*)malloc(sizeof(TYPE) * size);
    }

    return ptr;
}

void rand_matrix(TYPE** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            ptr[i][j] = (rand() % (MAX_RAND + 1)) / (TYPE)(rand() % MAX_RAND + 1);
        }
    }
}

void print_matrix(TYPE** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            printf("%4.2f ", ptr[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void clean_matrix(TYPE** ptr, unsigned size) {
    for (int i = 0; i < size; i++) {
        free(ptr[i]);
    }
    free(ptr);
}

#ifdef AUTO_VECTORIZE_NO_OPENMP
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {
    TYPE** result = alloc_matrix(size);

    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[i][k] += ptr_1[i][j] * ptr_2[j][k];
            }
        }
    }

    return result;
}
#endif

#ifdef AUTO_VECTORIZE_OPENMP
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {

    int i, j, k;
    int chunk = 30;
    TYPE **result = alloc_matrix(size);

    #pragma omp parallel shared(ptr_1, ptr_2, size, result, chunk) private(i, j, k)
    {
        #pragma omp for schedule (static, chunk)
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                for (k = 0; k < size; k++) {
                    result[i][k] += ptr_1[i][j] * ptr_2[j][k];
                }
            }
        }
    }
    return result;

}
#endif

//#ifdef MY_VECTORIZE_NO_OPENMP
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {
    TYPE** result = alloc_matrix(size);

    __m128 a_line, b_line, r_line;
    TYPE temp[2] = {0, 0};

    for (int i = 0; i < size; i++) {
        a_line = _mm_load_ps(a)
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                temp[0] = ptr_1[i][k];
                temp[1] = ptr_1[i+1][k];

                result[i][k] += ptr_1[i][j] * ptr_2[j][k];
            }
        }
    }

    return result;
}
//#endif

#ifdef MY_VECTORIZE_OPENMP
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {
    TYPE** result = alloc_matrix(size);

    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[i][k] += ptr_1[i][j] * ptr_2[j][k];
            }
        }
    }

    return result;
}
#endif

long time_diff(clock_t t1, clock_t t2) {
    long elapsed;
    elapsed = (long) (((double)t2 - t1) / CLOCKS_PER_SEC * 1000);
    return elapsed;
}

int main() {
    srand((unsigned int) time(NULL));

    TYPE** mx1 = alloc_matrix(N);
    TYPE** mx2 = alloc_matrix(N);

    rand_matrix(mx1, N);
    rand_matrix(mx2, N);

    double start = omp_get_wtime();

    TYPE** mx_res = multiply_matrix(mx1, mx2, N);

    double end = omp_get_wtime();

    printf("%.0f ms\n", (end - start) * 1000);

    clean_matrix(mx1, N);
    clean_matrix(mx2, N);
    clean_matrix(mx_res, N);
    return 0;
}