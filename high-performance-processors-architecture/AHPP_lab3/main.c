#include <stdio.h>
#include <stdlib.h>

#if defined(__i386__)

static __inline__ unsigned long long rdtsc(void)
{
    unsigned long long int x;
    __asm__ volatile (".byte 0x0f, 0x31" : "=A" (x));
    return x;
}

#elif defined(__x86_64__)

static __inline__ unsigned long long rdtsc(void)
{
    unsigned hi = 0, lo;
    __asm__ __volatile__ ("rdtsc" : "=a"(lo), "=d"(hi));
    return ( (unsigned long long)lo)|( ((unsigned long long)hi)<<32 );
}

#endif


int main(int argc, char** argv) {

    if (argc < 2) {
        printf("Wrong parameters count!");
    }

    unsigned int N = (unsigned int) atoi(argv[1]);

    if (N <= 0 || !N) {
        printf("N must be a positive int");
        exit(1);
    }

    unsigned long long elapsed = 0;
    elapsed = rdtsc();

    for (int i = 0; i < N; i++) {
        __asm__ ("or %eax, %ebx;"
                 "or %eax, %ebx;"
                 "or %eax, %ebx;"
                 "or %eax, %ebx;");
    }

    unsigned long long latency_elapsed = rdtsc() - elapsed;

    unsigned int latency_op_count = 4 * N;

    double latency = latency_elapsed / (double) latency_op_count;

    elapsed = rdtsc();

    for (int i = 0; i < N; i++) {
        __asm__ ("or %eax, %eax;"
                "or %ebx, %ebx;"
                "or %ecx, %ecx;"
                "or %edx, %edx;");
    }

    unsigned long long throughput_elapsed = rdtsc() - elapsed;

    unsigned int throughput_op_count = 4 * N;

    double throughput = throughput_elapsed / (double) throughput_op_count;

    printf("Latency ticks elapsed: %llu \n", latency_elapsed);
    printf("Latency: %.2lf \n", latency);

    printf("Throughput ticks elapsed: %llu \n", throughput_elapsed);
    printf("Throughput: %.2lf \n", throughput);
    return 0;
}