.text 0x00400000
.globl main
main:
li $t0, 10
li $t1, 0
looptest:
blt $t0, $t1, endloop
move $a0, $t1
li $v0, 1
syscall
la $a0, line
li $v0, 4
syscall
addu $t1, $t1, 1
j looptest
endloop:
li $v0, 10
syscall

.data
line:
.asciiz "\n"