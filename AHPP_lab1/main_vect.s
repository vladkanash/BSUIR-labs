	.file	"main.c"
	.text
	.p2align 4,,15
	.globl	alloc_matrix
	.type	alloc_matrix, @function
alloc_matrix:
.LFB22:
	.cfi_startproc
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	movl	%edi, %r12d
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	movl	%edi, %ebp
	leaq	0(,%r12,8), %rdi
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	call	malloc
	testl	%ebp, %ebp
	movq	%rax, %r13
	je	.L6
	movq	%rax, %rbx
	leal	-1(%rbp), %eax
	salq	$2, %r12
	leaq	8(%r13,%rax,8), %rbp
	.p2align 4,,10
	.p2align 3
.L4:
	movq	%r12, %rdi
	addq	$8, %rbx
	call	malloc
	movq	%rax, -8(%rbx)
	cmpq	%rbp, %rbx
	jne	.L4
.L6:
	addq	$8, %rsp
	.cfi_def_cfa_offset 40
	movq	%r13, %rax
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE22:
	.size	alloc_matrix, .-alloc_matrix
	.p2align 4,,15
	.globl	rand_matrix
	.type	rand_matrix, @function
rand_matrix:
.LFB23:
	.cfi_startproc
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	movl	%esi, %eax
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	subq	$24, %rsp
	.cfi_def_cfa_offset 80
	testl	%esi, %esi
	movl	%esi, 12(%rsp)
	je	.L17
	subl	$1, %eax
	movq	%rdi, %rbx
	xorl	%r13d, %r13d
	leaq	4(,%rax,4), %r12
	movl	$1717986919, %ebp
	.p2align 4,,10
	.p2align 3
.L11:
	xorl	%r15d, %r15d
	.p2align 4,,10
	.p2align 3
.L13:
	movq	%r15, %r14
	addq	(%rbx), %r14
	addq	$4, %r15
	call	rand
	movl	%eax, %ecx
	imull	%ebp
	movl	%ecx, %eax
	sarl	$31, %eax
	sarl	%edx
	subl	%eax, %edx
	leal	(%rdx,%rdx,4), %eax
	subl	%eax, %ecx
	cmpq	%r12, %r15
	movl	%ecx, (%r14)
	jne	.L13
	addl	$1, %r13d
	addq	$8, %rbx
	cmpl	12(%rsp), %r13d
	jne	.L11
.L17:
	addq	$24, %rsp
	.cfi_def_cfa_offset 56
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE23:
	.size	rand_matrix, .-rand_matrix
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"%4d "
.LC1:
	.string	"\n"
	.text
	.p2align 4,,15
	.globl	print_matrix
	.type	print_matrix, @function
print_matrix:
.LFB24:
	.cfi_startproc
	pushq	%r14
	.cfi_def_cfa_offset 16
	.cfi_offset 14, -16
	testl	%esi, %esi
	movl	%esi, %r14d
	pushq	%r13
	.cfi_def_cfa_offset 24
	.cfi_offset 13, -24
	pushq	%r12
	.cfi_def_cfa_offset 32
	.cfi_offset 12, -32
	pushq	%rbp
	.cfi_def_cfa_offset 40
	.cfi_offset 6, -40
	pushq	%rbx
	.cfi_def_cfa_offset 48
	.cfi_offset 3, -48
	je	.L20
	leal	-1(%rsi), %eax
	movq	%rdi, %rbp
	xorl	%r13d, %r13d
	leaq	4(,%rax,4), %r12
	.p2align 4,,10
	.p2align 3
.L21:
	xorl	%ebx, %ebx
	.p2align 4,,10
	.p2align 3
.L23:
	movq	0(%rbp), %rax
	movl	$.LC0, %esi
	movl	$1, %edi
	movl	(%rax,%rbx), %edx
	xorl	%eax, %eax
	addq	$4, %rbx
	call	__printf_chk
	cmpq	%r12, %rbx
	jne	.L23
	xorl	%eax, %eax
	movl	$.LC1, %esi
	movl	$1, %edi
	addl	$1, %r13d
	addq	$8, %rbp
	call	__printf_chk
	cmpl	%r14d, %r13d
	jne	.L21
.L20:
	popq	%rbx
	.cfi_def_cfa_offset 40
	popq	%rbp
	.cfi_def_cfa_offset 32
	popq	%r12
	.cfi_def_cfa_offset 24
	popq	%r13
	.cfi_def_cfa_offset 16
	popq	%r14
	.cfi_def_cfa_offset 8
	movl	$.LC1, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	jmp	__printf_chk
	.cfi_endproc
.LFE24:
	.size	print_matrix, .-print_matrix
	.p2align 4,,15
	.globl	clean_matrix
	.type	clean_matrix, @function
clean_matrix:
.LFB25:
	.cfi_startproc
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	testl	%esi, %esi
	movq	%rdi, %r12
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	je	.L30
	leal	-1(%rsi), %eax
	movq	%rdi, %rbx
	leaq	8(%rdi,%rax,8), %rbp
	.p2align 4,,10
	.p2align 3
.L31:
	movq	(%rbx), %rdi
	addq	$8, %rbx
	call	free
	cmpq	%rbp, %rbx
	jne	.L31
.L30:
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	movq	%r12, %rdi
	popq	%r12
	.cfi_def_cfa_offset 8
	jmp	free
	.cfi_endproc
.LFE25:
	.size	clean_matrix, .-clean_matrix
	.p2align 4,,15
	.globl	multiply_matrix
	.type	multiply_matrix, @function
multiply_matrix:
.LFB26:
	.cfi_startproc
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	movq	%rsi, %r12
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	subq	$56, %rsp
	.cfi_def_cfa_offset 112
	movq	%rdi, 40(%rsp)
	movl	%edx, %edi
	movl	%edx, (%rsp)
	call	alloc_matrix
	movl	(%rsp), %edx
	movq	%rax, 24(%rsp)
	testl	%edx, %edx
	je	.L60
	movl	%edx, %ebp
	movl	%edx, %eax
	movq	$0, 16(%rsp)
	shrl	$2, %ebp
	salq	$2, %rax
	leal	0(,%rbp,4), %ebx
	movq	%rax, 32(%rsp)
	movslq	%ebx, %rax
	leaq	0(,%rax,4), %r15
	movl	%eax, 12(%rsp)
	.p2align 4,,10
	.p2align 3
.L35:
	movq	16(%rsp), %rax
	movq	24(%rsp), %rdi
	xorl	%r10d, %r10d
	movq	40(%rsp), %rsi
	movq	(%rdi,%rax,8), %rdi
	movq	(%rsi,%rax,8), %rsi
	movq	32(%rsp), %rax
	leaq	16(%rdi), %r14
	leaq	(%rdi,%rax), %r13
	leaq	(%rdi,%r15), %rax
	movq	%rax, (%rsp)
	.p2align 4,,10
	.p2align 3
.L48:
	leaq	4(%rsi), %r11
	movq	(%r12,%r10,8), %r9
	cmpq	%r11, %rdi
	setnb	%al
	cmpq	%r13, %rsi
	setnb	%cl
	orl	%ecx, %eax
	cmpl	$3, %edx
	seta	%cl
	testb	%cl, %al
	je	.L47
	leaq	16(%r9), %rax
	cmpq	%rax, %rdi
	setnb	%cl
	cmpq	%r14, %r9
	setnb	%al
	orb	%al, %cl
	je	.L47
	testl	%ebx, %ebx
	je	.L39
	xorl	%eax, %eax
	xorl	%ecx, %ecx
.L36:
	vmovdqu	(%rdi,%rax), %xmm1
	addl	$1, %ecx
	vmovdqu	(%r9,%rax), %xmm0
	vbroadcastss	(%rsi), %xmm2
	vpmulld	%xmm0, %xmm2, %xmm0
	vpaddd	%xmm0, %xmm1, %xmm0
	vmovdqu	%xmm0, (%rdi,%rax)
	addq	$16, %rax
	cmpl	%ebp, %ecx
	jb	.L36
	cmpl	%ebx, %edx
	je	.L37
.L39:
	movq	(%rsp), %rax
	movl	12(%rsp), %r8d
	addq	%r15, %r9
.L43:
	movl	(%rsi), %ecx
	addl	$1, %r8d
	addq	$4, %r9
	imull	-4(%r9), %ecx
	addl	%ecx, (%rax)
	addq	$4, %rax
	cmpl	%r8d, %edx
	ja	.L43
.L37:
	addq	$1, %r10
	cmpl	%r10d, %edx
	jbe	.L44
.L62:
	movq	%r11, %rsi
	jmp	.L48
	.p2align 4,,10
	.p2align 3
.L47:
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L38:
	movl	(%rsi), %ecx
	imull	(%r9,%rax,4), %ecx
	addl	%ecx, (%rdi,%rax,4)
	addq	$1, %rax
	cmpl	%eax, %edx
	ja	.L38
	addq	$1, %r10
	cmpl	%r10d, %edx
	ja	.L62
	.p2align 4,,10
	.p2align 3
.L44:
	addq	$1, 16(%rsp)
	cmpl	16(%rsp), %edx
	ja	.L35
.L60:
	movq	24(%rsp), %rax
	addq	$56, %rsp
	.cfi_def_cfa_offset 56
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE26:
	.size	multiply_matrix, .-multiply_matrix
	.p2align 4,,15
	.globl	time_diff
	.type	time_diff, @function
time_diff:
.LFB27:
	.cfi_startproc
	vcvtsi2sdq	%rsi, %xmm1, %xmm1
	vcvtsi2sdq	%rdi, %xmm0, %xmm0
	vsubsd	%xmm0, %xmm1, %xmm0
	vdivsd	.LC2(%rip), %xmm0, %xmm0
	vmulsd	.LC3(%rip), %xmm0, %xmm0
	vcvttsd2siq	%xmm0, %rax
	ret
	.cfi_endproc
.LFE27:
	.size	time_diff, .-time_diff
	.section	.rodata.str1.1
.LC4:
	.string	"elapsed: %ld ms\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB28:
	.cfi_startproc
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	xorl	%edi, %edi
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	call	time
	movl	%eax, %edi
	call	srand
	movl	$1280, %edi
	call	alloc_matrix
	movl	$1280, %edi
	movq	%rax, %rbp
	call	alloc_matrix
	movq	%rbp, %rdi
	movq	%rax, %rbx
	movl	$1280, %esi
	call	rand_matrix
	movq	%rbx, %rdi
	movl	$1280, %esi
	call	rand_matrix
	call	clock
	movq	%rbx, %rsi
	movq	%rbp, %rdi
	movq	%rax, %r13
	movl	$1280, %edx
	call	multiply_matrix
	movq	%rax, %r12
	call	clock
	vcvtsi2sdq	%r13, %xmm0, %xmm0
	movl	$.LC4, %esi
	vcvtsi2sdq	%rax, %xmm1, %xmm1
	movl	$1, %edi
	xorl	%eax, %eax
	vsubsd	%xmm0, %xmm1, %xmm0
	vdivsd	.LC2(%rip), %xmm0, %xmm0
	vmulsd	.LC3(%rip), %xmm0, %xmm0
	vcvttsd2siq	%xmm0, %rdx
	call	__printf_chk
	movq	%rbp, %rdi
	movl	$1280, %esi
	call	clean_matrix
	movq	%rbx, %rdi
	movl	$1280, %esi
	call	clean_matrix
	movq	%r12, %rdi
	movl	$1280, %esi
	call	clean_matrix
	addq	$8, %rsp
	.cfi_def_cfa_offset 40
	xorl	%eax, %eax
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE28:
	.size	main, .-main
	.section	.rodata.cst8,"aM",@progbits,8
	.align 8
.LC2:
	.long	0
	.long	1093567616
	.align 8
.LC3:
	.long	0
	.long	1083129856
	.ident	"GCC: (Ubuntu 4.8.4-2ubuntu1~14.04) 4.8.4"
	.section	.note.GNU-stack,"",@progbits
