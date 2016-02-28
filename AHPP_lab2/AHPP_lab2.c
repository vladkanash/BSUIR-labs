#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

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

#ifdef MY_VECTORIZE_NO_OPENMP
#include <emmintrin.h>
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {
    TYPE** result = alloc_matrix(size);

    for(size_t i = 0; i < size; i++)
    {
        TYPE* res_line = result[i];
        for(size_t j = 0; j < size; j++)
        {
            TYPE m1_value = ptr_1[i][j];
            TYPE* m2_line = ptr_2[j];

            __m128 v1, v2, v3;
            v1 = _mm_load1_ps(&m1_value);
            for(size_t k = sizeof(TYPE); k < size; k += 2*sizeof(TYPE))
            {
                v2 = _mm_load_ps(res_line + k);
                v3 = _mm_load_ps(m2_line + k);
                v3 = _mm_mul_ps(v3, v1);
                v2 = _mm_add_ps(v2, v3);
                _mm_store_ps(res_line + k, v2);

                v2 = _mm_load_ps(res_line + k - sizeof(TYPE));
                v3 = _mm_load_ps(m2_line + k- sizeof(TYPE));
                v3 = _mm_mul_ps(v3, v1);
                v2 = _mm_add_ps(v2, v3);
                _mm_store_ps(res_line + k- sizeof(TYPE), v2);
            }
        }
    }
    return result;
}
#endif

#ifdef MY_VECTORIZE_OPENMP
#include <emmintrin.h>
TYPE** multiply_matrix(TYPE** ptr_1, TYPE** ptr_2, unsigned size) {
    TYPE** result = alloc_matrix(size);
    int i, j, k;
    int chunk = 30;

    #pragma omp parallel shared(ptr_1, ptr_2, size, result, chunk) private(i, j, k)
    {
        #pragma omp for schedule (static, chunk)
        for (i = 0; i < size; i++) {
            TYPE *res_line = result[i];
            for (j = 0; j < size; j++) {
                TYPE m1_value = ptr_1[i][j];
                TYPE *m2_line = ptr_2[j];

                __m128 v1, v2, v3;
                v1 = _mm_load1_ps(&m1_value);
                for (k = sizeof(TYPE); k < size; k += 2 * sizeof(TYPE)) {
                    v2 = _mm_load_ps(res_line + k);
                    v3 = _mm_load_ps(m2_line + k);
                    v3 = _mm_mul_ps(v3, v1);
                    v2 = _mm_add_ps(v2, v3);
                    _mm_store_ps(res_line + k, v2);

                    v2 = _mm_load_ps(res_line + k - sizeof(TYPE));
                    v3 = _mm_load_ps(m2_line + k - sizeof(TYPE));
                    v3 = _mm_mul_ps(v3, v1);
                    v2 = _mm_add_ps(v2, v3);
                    _mm_store_ps(res_line + k - sizeof(TYPE), v2);
                }
            }
        }
    }
    return result;
}
#endif

int main(int argc, char** argv) {
    srand((unsigned int) time(NULL));

    if (argc != 2) {
        printf("You must specify N parameter - ");
        exit(1);
    }

    int count = atoi(argv[1]);

    if (count <= 0 || !count) {
        printf("N must be a positive int");
        exit(1);
    }

    unsigned int N = (unsigned int) count;

    TYPE** mx1 = alloc_matrix(N);
    TYPE** mx2 = alloc_matrix(N);

    rand_matrix(mx1, N);
    rand_matrix(mx2, N);

    double start = omp_get_wtime();

    TYPE** mx_res = multiply_matrix(mx1, mx2, N);

    double end = omp_get_wtime();

    printf("%.0f ms\n", (end - start) * 1000);
    //print_matrix(mx_res, N);

    clean_matrix(mx1, N);
    clean_matrix(mx2, N);
    clean_matrix(mx_res, N);
    return 0;
}