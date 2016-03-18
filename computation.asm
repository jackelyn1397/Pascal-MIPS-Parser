.text 0x00400000
.globl main
main:
li $v0, 5
syscall
move $t0, $v0
li $v0, 5
syscall
move $t1, $v0
addu $a0, $t0, $t1
li $v0, 1
syscall