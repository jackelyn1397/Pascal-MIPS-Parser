.text 0x00400000
.globl main
main:
li $v0, 5
syscall
move $t0, $v0
li $v0, 5
syscall
move $t1, $v0
bgt $t0, $t1, greater
move $a0, $t1
j after
greater:
move $a0, $t0
after:
li $v0, 1
syscall