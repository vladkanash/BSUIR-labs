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
	je	.L5
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
.L5:
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
	je	.L8
	subl	$1, %eax
	movq	%rdi, %rbx
	xorl	%r13d, %r13d
	leaq	4(,%rax,4), %r12
	movl	$1717986919, %ebp
	.p2align 4,,10
	.p2align 3
.L10:
	xorl	%r15d, %r15d
	.p2align 4,,10
	.p2align 3
.L12:
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
	jne	.L12
	addl	$1, %r13d
	addq	$8, %rbx
	cmpl	12(%rsp), %r13d
	jne	.L10
.L8:
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
	je	.L18
	leal	-1(%rsi), %eax
	movq	%rdi, %rbp
	xorl	%r13d, %r13d
	leaq	4(,%rax,4), %r12
	.p2align 4,,10
	.p2align 3
.L19:
	xorl	%ebx, %ebx
	.p2align 4,,10
	.p2align 3
.L21:
	movq	0(%rbp), %rax
	movl	$.LC0, %esi
	movl	$1, %edi
	movl	(%rax,%rbx), %edx
	xorl	%eax, %eax
	addq	$4, %rbx
	call	__printf_chk
	cmpq	%r12, %rbx
	jne	.L21
	xorl	%eax, %eax
	movl	$.LC1, %esi
	movl	$1, %edi
	addl	$1, %r13d
	addq	$8, %rbp
	call	__printf_chk
	cmpl	%r14d, %r13d
	jne	.L19
.L18:
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
	je	.L28
	leal	-1(%rsi), %eax
	movq	%rdi, %rbx
	leaq	8(%rdi,%rax,8), %rbp
	.p2align 4,,10
	.p2align 3
.L29:
	movq	(%rbx), %rdi
	addq	$8, %rbx
	call	free
	cmpq	%rbp, %rbx
	jne	.L29
.L28:
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
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	movl	%edx, %r13d
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	movq	%rdi, %r12
	movl	%edx, %edi
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	movq	%rsi, %rbx
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	call	alloc_matrix
	testl	%r13d, %r13d
	je	.L32
	xorl	%ebp, %ebp
.L33:
	movq	(%rax,%rbp,8), %r9
	movq	(%r12,%rbp,8), %rsi
	xorl	%r10d, %r10d
	.p2align 4,,10
	.p2align 3
.L37:
	movq	(%rbx,%r10,8), %r11
	xorl	%ecx, %ecx
	.p2align 4,,10
	.p2align 3
.L36:
	movl	(%rsi,%r10,4), %r8d
	imull	(%r11,%rcx,4), %r8d
	addl	%r8d, (%r9,%rcx,4)
	addq	$1, %rcx
	cmpl	%ecx, %r13d
	ja	.L36
	addq	$1, %r10
	cmpl	%r10d, %r13d
	ja	.L37
	addq	$1, %rbp
	cmpl	%ebp, %r13d
	ja	.L33
.L32:
	addq	$8, %rsp
	.cfi_def_cfa_offset 40
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
.LFE26:
	.size	multiply_matrix, .-multiply_matrix
	.p2align 4,,15
	.globl	time_diff
	.type	time_diff, @function
time_diff:
.LFB27:
	.cfi_startproc
	movq	%rsi, -8(%rsp)
	fnstcw	-10(%rsp)
	movzwl	-10(%rsp), %eax
	fildq	-8(%rsp)
	movq	%rdi, -8(%rsp)
	fildq	-8(%rsp)
	orb	$12, %ah
	fsubrp	%st, %st(1)
	movw	%ax, -12(%rsp)
	fdivs	.LC2(%rip)
	fmuls	.LC3(%rip)
	fldcw	-12(%rsp)
	fistpq	-8(%rsp)
	fldcw	-10(%rsp)
	movq	-8(%rsp), %rax
	ret
	.cfi_endproc
.LFE27:
	.size	time_diff, .-time_diff
	.section	.rodata.str1.1
.LC5:
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
	subq	$24, %rsp
	.cfi_def_cfa_offset 64
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
	movl	$1280, %edx
	movq	%rax, %r13
	call	multiply_matrix
	movq	%rax, %r12
	call	clock
	fnstcw	6(%rsp)
	movq	%rax, 8(%rsp)
	movzwl	6(%rsp), %edx
	movl	$.LC5, %esi
	fildq	8(%rsp)
	movq	%r13, 8(%rsp)
	movl	$1, %edi
	xorl	%eax, %eax
	fildq	8(%rsp)
	orb	$12, %dh
	fsubrp	%st, %st(1)
	movw	%dx, 4(%rsp)
	fdivs	.LC2(%rip)
	fmuls	.LC3(%rip)
	fldcw	4(%rsp)
	fistpq	8(%rsp)
	fldcw	6(%rsp)
	movq	8(%rsp), %rdx
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
	addq	$24, %rsp
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
	.section	.rodata.cst4,"aM",@progbits,4
	.align 4
.LC2:
	.long	1232348160
	.align 4
.LC3:
	.long	1148846080
	.ident	"GCC: (Ubuntu 4.8.4-2ubuntu1~14.04) 4.8.4"
	.section	.note.GNU-stack,"",@progbits
