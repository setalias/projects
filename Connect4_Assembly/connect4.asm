###################################################
#	Sandy Lee -- Connect4  // Scoring Incomplete  #
###################################################

.data

	# matrix is 6 rows x 7 columns
	# new column every 4 bytes 
	# new row every 28 bytes
	player1Grid:	.word	0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0

	player2Grid:	.word	0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0


	stack_beg: 	.word	0 : 40
	stack_end:	
	
	rastack:	.word	0 : 20
	rastack_end:	
	# -4 playGame
	# -8 player selections
	# -12 drawDisk
	# -16 clearBoard
	# -20 init
	
	max_round:	.word	5	#max number of rounds
	current_round:	.word	0	#current round
	Base:		.word		0x10040000

	
	gameMax:	.word	0x2A
	errorFlag:	.word	0	#set when error occurs
	playerFlag:	.word	0	#set to know which player game is on
	memoryLocation:	.word	0	#memory location to indicate player placed a disk
	
	col1:		.word	0	#set to know which rows are filled
	col2:		.word	0
	col3:		.word	0
	col4:		.word	0
	col5:		.word	0
	col6:		.word	0
	col7:		.word	0
		
		
	rowTable:
		.word	212
		.word	172
		.word	132
		.word	92
		.word	52
		.word	12
		

	columnTable:
		.word	8
		.word	47
		.word	83
		.word	119
		.word	155
		.word	191
		.word	227	
				
	selection:	.word	0	#hold user selection
	
	newline:	.asciiz 	"\n"
	player1Input:	.asciiz		"player 1 selection: "
	player2Input:	.asciiz		"player 2 selection: "
	player1wins:	.asciiz		"Player 1 WINS!"
	player2wins:	.asciiz		"Player 2 WINS!"
	computerWins:	.asciiz		"Computer WINS!"
	tie:		.asciiz		"TIE!"
	colMaxError:	.asciiz		"The column you've chosen is either full or does not exist, please choose another"
	
	ColorTable:
		.word	0x000000		#black
		.word	0x1E90FF		#blue
		.word	0x00ff00		#green
		.word	0xff0000		#red	
		.word	0xFFA500			#orange
		.word	0xff00ff			#blue + red
		.word	0xffff00			#green + red = yellow
		.word	0xffffff		#white

	CircleLengthTable:
		.word   24
		.word   24
		.word   22
		.word   22
		.word   20
		.word   20
		.word   18
		.word   16
		.word   14
		.word   10
		.word   6
		.word	2


################################################## DIGIT CODE BELOW ##################################################

        .word   0 : 40
Stack:

Colors: .word   0x000000        # background color (black)
        .word   0xffffff        # foreground color (white)

DigitTable:
        .byte   ' ', 0,0,0,0,0,0,0,0,0,0,0,0
        .byte   '0', 0x7e,0xff,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xff,0x7e
        .byte   '1', 0x38,0x78,0xf8,0x18,0x18,0x18,0x18,0x18,0x18,0x18,0x18,0x18
        .byte   '2', 0x7e,0xff,0x83,0x06,0x0c,0x18,0x30,0x60,0xc0,0xc1,0xff,0x7e
        .byte   '3', 0x7e,0xff,0x83,0x03,0x03,0x1e,0x1e,0x03,0x03,0x83,0xff,0x7e
        .byte   '4', 0xc3,0xc3,0xc3,0xc3,0xc3,0xff,0x7f,0x03,0x03,0x03,0x03,0x03
        .byte   '5', 0xff,0xff,0xc0,0xc0,0xc0,0xfe,0x7f,0x03,0x03,0x83,0xff,0x7f
        .byte   '6', 0xc0,0xc0,0xc0,0xc0,0xc0,0xfe,0xfe,0xc3,0xc3,0xc3,0xff,0x7e
        .byte   '7', 0x7e,0xff,0x03,0x06,0x06,0x0c,0x0c,0x18,0x18,0x30,0x30,0x60
        .byte   '8', 0x7e,0xff,0xc3,0xc3,0xc3,0x7e,0x7e,0xc3,0xc3,0xc3,0xff,0x7e
        .byte   '9', 0x7e,0xff,0xc3,0xc3,0xc3,0x7f,0x7f,0x03,0x03,0x03,0x03,0x03
        .byte   '+', 0x00,0x00,0x00,0x18,0x18,0x7e,0x7e,0x18,0x18,0x00,0x00,0x00
        .byte   '-', 0x00,0x00,0x00,0x00,0x00,0x7e,0x7e,0x00,0x00,0x00,0x00,0x00
        .byte   '*', 0x00,0x00,0x00,0x66,0x3c,0x18,0x18,0x3c,0x66,0x00,0x00,0x00
        .byte   '/', 0x00,0x00,0x18,0x18,0x00,0x7e,0x7e,0x00,0x18,0x18,0x00,0x00
        .byte   '=', 0x00,0x00,0x00,0x00,0x7e,0x00,0x7e,0x00,0x00,0x00,0x00,0x00
        .byte   'A', 0x18,0x3c,0x66,0xc3,0xc3,0xc3,0xff,0xff,0xc3,0xc3,0xc3,0xc3
        .byte   'B', 0xfc,0xfe,0xc3,0xc3,0xc3,0xfe,0xfe,0xc3,0xc3,0xc3,0xfe,0xfc
        .byte   'C', 0x7e,0xff,0xc1,0xc0,0xc0,0xc0,0xc0,0xc0,0xc0,0xc1,0xff,0x7e
        .byte   'D', 0xfc,0xfe,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xc3,0xfe,0xfc
        .byte   'E', 0xff,0xff,0xc0,0xc0,0xc0,0xfe,0xfe,0xc0,0xc0,0xc0,0xff,0xff
        .byte   'F', 0xff,0xff,0xc0,0xc0,0xc0,0xfe,0xfe,0xc0,0xc0,0xc0,0xc0,0xc0
# add additional characters here....
# first byte is the ascii character
# next 12 bytes are the pixels that are "on" for each of the 12 lines
        .byte    0, 0,0,0,0,0,0,0,0,0,0,0,0




#  0x80----  ----0x08
#  0x40--- || ---0x04
#  0x20-- |||| --0x02
#  0x10- |||||| -0x01
#       ||||||||
#       84218421

#   1   ...xx...      0x18
#   2   ..xxxx..      0x3c
#   3   .xx..xx.      0x66
#   4   xx....xx      0xc3
#   5   xx....xx      0xc3
#   6   xx....xx      0xc3
#   7   xxxxxxxx      0xff
#   8   xxxxxxxx      0xff
#   9   xx....xx      0xc3
#  10   xx....xx      0xc3
#  11   xx....xx      0xc3
#  12   xx....xx      0xc3



Number1:  .asciiz "1"
Number2:  .asciiz "2"
Number3:  .asciiz "3"
Number4:  .asciiz "4"
Number5:  .asciiz "5"
Number6:  .asciiz "6"
Number7:  .asciiz "7"

################################################## DIGIT CODE ABOVE ##################################################


.text

	jal	init			# initialization
	jal	drawGame
	jal 	playGame
	

	#exit if nothing else to do
	li $v0, 10
	syscall


#################################################
#		Game Setup			#
#################################################

init:
	# Procedure: init to initialize for next game
	# clear sequence, clear display, set max = 0, set seed
	# Input: $s7 holds current rounds counter
	la	$sp, rastack_end	#store $ra
	addiu	$sp, $sp, -20
	sw	$ra, 0($sp)

	jal	clearBoard	
	la	$s7, playerFlag		#flag for turn / win

	la	$sp, rastack_end	#restore $ra
	addiu	$sp, $sp, -20
	lw	$ra, 0($sp)
	jr	$ra
	
	
drawGame:
	#Procedure: drawGame
	#draws the horizontal and vertical board lines
	
	move	$s2, $ra	#backup ra
	move	$s4, $sp	#backup sp
	li	$t1, 6
	li	$a1, 32		#y axis start
				
	drawx:	
	li	$a0, 0		#x axis start
	li	$a2, 7		#color
	li	$a3, 256	#length
	jal	horzLine
	addiu	$t1, $t1, -1
	addiu	$a1, $a1, 40
	bnez	$t1, drawx
	li	$t1, 6
	li	$a0, 40		#x axis start

	
	j	drawy
	
	drawy:
	li	$a1, 0		#y axis start
	li	$a2, 7		#color
	li	$a3, 256	#length
	jal	vertLine
	addiu	$t1, $t1, -1
	addiu	$a0, $a0, 36
	bnez	$t1, drawy
	
	j	drawnums
	
	drawnums:

	la      $sp, Stack
        li      $a0, 15        
        li      $a1, 239
        la      $a2, Number1
        jal     OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number2	
	jal	OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number3	
	jal	OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number4	
	jal	OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number5	
	jal	OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number6
	jal	OutText
        addiu	$a0, $a0, 36
        li	$a1, 239
        la	$a2, Number7
	jal	OutText
	
	move	$sp, $s4	#restore sp
	move	$ra, $s2	#restore ra

	jr	$ra

#################################################
#		Game Logic			#
#################################################

playGame:
	
	#call player1
	jal	checkGameMax
	jal	getPlayer1	
	jal	gridLocation	#returns location in $s0
	jal	markGrid
	jal	drawDisk
	
	#call player2
	jal	checkGameMax
	jal	getPlayer2
	jal	gridLocation	#returns location in $s0
	jal	markGrid
	jal	drawDisk	

	j	playGame	
	
checkGameMax:
	lw 	$t0, gameMax
	beqz	$t0, gameOver
	addiu	$t0, $t0, -1
	sw	$t0, gameMax
	jr	$ra

getPlayer1:
	
	lw	$t0, errorFlag			
	beq	$t0, 0, player1FirstPass	#if error flag is not set then 
	beq	$t0, 1, player1Main		#if error flag is set
	
	player1FirstPass:
		la	$sp, rastack_end	#store $ra
		addiu	$sp, $sp, -8
		sw	$ra, 0($sp)	
		j	player1Main
		
	player1Main:
		
		li 	$t0, 0			
		sw	$t0, errorFlag		#resetErrorFlag	
	
		li	$t0, 1
		sw	$t0, playerFlag
	

		li	$v0, 4
		la	$a0, newline
		syscall
		la	$a0, player1Input
		syscall
	
		li	$v0, 5
		syscall
		sw	$v0, selection
		move	$a1, $v0		#a1 now holds column selection
		addiu	$a1, $a1, -1		#subtract 1 from col to line up with 0 indexing
		jal	getRowFromCol		#a2 now holds current row
		li	$a3, 3			#a3 now holds red
	
	
		la	$sp, rastack_end	#restore $ra
		addiu	$sp, $sp, -8
		lw	$ra, 0($sp)	
		jr	$ra

getPlayer2:

	lw	$t0, errorFlag			
	beq	$t0, 0, player2FirstPass	#if error flag is not set then 
	beq	$t0, 1, player2Main		#if error flag is set

	player2FirstPass:
		la	$sp, rastack_end	#store $ra
		addiu	$sp, $sp, -8
		sw	$ra, 0($sp)	
		j	player2Main
		
	player2Main:
		li 	$t0, 0			
		sw	$t0, errorFlag		#resetErrorFlag	

		li	$t0, 2
		sw	$t0, playerFlag
	
		li	$v0, 4
		la	$a0, newline
		syscall
		la	$a0, player2Input
		syscall
	
		li	$v0, 5
		syscall
		sw	$v0, selection
		move	$a1, $v0		#a1 now holds column selection
		addiu	$a1, $a1, -1		#subtract 1 from col to line up with 0 indexing
		jal	getRowFromCol		#a2 now holds current row
		li	$a3, 1			#a3 now holds blue
	
	
		la	$sp, rastack_end	#restore $ra
		addiu	$sp, $sp, -8
		lw	$ra, 0($sp)	
		jr	$ra

colMaxed:
	li	$v0, 4
	la	$a0, colMaxError
	syscall
	
	li	$t0, 1
	sw	$t0, errorFlag
	
	lw	$t0, playerFlag	
	beq	$t0, 1, getPlayer1
	beq	$t0, 2, getPlayer2

getRowFromCol:
	
	lw	$a0, selection
	beq	$a0, 1, c1
	beq	$a0, 2, c2
	beq	$a0, 3, c3
	beq	$a0, 4, c4
	beq	$a0, 5, c5
	beq	$a0, 6, c6
	beq	$a0, 7, c7
	j	colMaxed	#if none of the above it's a column error
	
	c1:
		lw	$a2, col1		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col1		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
	
	c2:
		lw	$a2, col2		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col2		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return		
	c3:
		lw	$a2, col3		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col3		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
		
	c4:
		lw	$a2, col4		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col4		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
	c5:
		lw	$a2, col5		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col5		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
		
	c6:
		lw	$a2, col6		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col6		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
		
	c7:
		lw	$a2, col7		#load current col row
		beq	$a2, 6, colMaxed	#check if column already maxed
		addiu	$a2, $a2, 1		#add one to row
		sw	$a2, col7		#store new col row for next
		addiu	$a2, $a2, -1		#subtract back to current row
		jr	$ra			#return
	

getCompPlayer:


#################################################
#		Win Logic			#
#################################################

#printGrid:
#	lw	$t0, playerFlag			#load playerflag
#	beq	$t0, 1, loadPlayer1print		#branch to player 1
#	j	loadPlayer2print			#else branch to player 2 / other player
#
#	loadPlayer1print:
#		la	$s0, player1Grid	#load base address of player 1 grid
#		j	printGridMain
#	
#	loadPlayer2print:
#		la	$s0, player2Grid	#load base address of player 2 grid
#		j	printGridMain
#
#	printGridMain:
#		
#		li	$t1, 0		#newline check
#		li	$t2, 42		#total iterations
#		loop:
#			addiu	$t1, $t1, 1	#add one to newline grid
#			beq	$t1, 8, printGridNewline
#			addiu	$s0, $s0, 4	#add offset to $s0
#			addiu	$t2, $t2, -1
#			
#			li	$v0, 1
#			lw	$a0, 0($s0)
#			syscall
#			
#			bnez	$t2, loop
#			jr	$ra
#			
#			
#		printGridNewline:
#			la	$a0, newline
#			li	$v0, 4
#			syscall
#			li	$t1, 0
#			j	loop
	

#################################################
#		Grid Tracking			#
#################################################

markGrid:
	#INPUT: gridLocation stored in $s0
	
	li	$t0, 1		# 1 will be put into location to signify disk
	sw	$t0, 0($s0)	# inset $t0 into gridLocation
	jr	$ra


gridLocation:
	#Procedure: gridLocation
	#gets the memory address of the grid location needed based on row, column, player
	#INPUT: $a0 as column
	#INPUT: $a1 as row
	#INPUT: playerFlag must be current
	#OUTPUT: $s0 returns memory location
	
	#6 rows 7 columns
	#new column every 4 bytes
	#new row every 28 bytes
	
	#ex: row comes in as 2, top down that is row 5, not row 2. Subtract from 6 to get 5.  
	#mul	$t3, $a1, -1	#make row negative
	#addiu	$t3, $t3, 6	#add negative row to 6 to get new row number top down instead of bottom up. 
	addiu	$t3, $a1, -1	#remove 1 from row for zero index
	addiu	$t4, $a0, -1	#remove 1 from column for zero index
	
	lw	$t0, playerFlag			#load playerflag
	beq	$t0, 1, loadPlayer1		#branch to player 1
	j	loadPlayer2			#else branch to player 2 / other player

	loadPlayer1:
		la	$s0, player1Grid	#load base address of player 1 grid
		j	gridLocationMain
	
	loadPlayer2:
		la	$s0, player2Grid	#load base address of player 2 grid
		j	gridLocationMain
	
	gridLocationMain:
		li	$t0, 0			#$t0 will be offset from base address
		li	$t1, 0			#t1 will be column offset
		li	$t2, 0			#t2 will be row offset
		
		mul	$t1, $t4, 4		#get column location
		mul	$t2, $t3, 28		#get row location
		addu	$t0, $t1, $t2		#add together
		
		addu	$s0, $s0, $t0		#add to base address
		
		jr	$ra		
		
	
		
#################################################
#		Draw Functions			#
#################################################


drawDisk:
	#Procedure: drawDisk
	#draws a disk in the selected location
	#Input: $a1 column
	#Input: $a2 row
	#Input: $a3 color

	la	$sp, rastack_end	#store $ra
	addiu	$sp, $sp, -12
	sw	$ra, 0($sp)	

	
	#get row location
	#li 	$a2, 0
	jal 	getRow		#v1 holds row
	move 	$s0, $v1	#backup row to s0
		
	#get column location
	move	$a2, $a1	#load column
	#li 	$a2, 3
	jal 	getColumn	#v1 holds column
	
	move	$a1, $s0	#load row to $a1
	move	$a0, $v1	#load column to a0

	move	$a2, $a3	#load color
	jal 	drawCircle

	
	la	$sp, rastack_end	#store $ra
	addiu	$sp, $sp, -12
	lw	$ra, 0($sp)			
	jr 	$ra

drawDot:
	#Procedure: drawDot
	#draws a single dot
	#Input: $a0 = x coordinate 0-31
	#Input: $a1 = y coordinate 0-31
	#Input: $a2 = color number 0-7
	
	la	$sp, stack_end
	addiu	$sp, $sp, -56	#make room on stack
	sw	$ra, 4($sp)	#backup ra
	sw	$a2, 0($sp)	#backup a2
	jal	calcAddr	#v0 has address for pixel
	lw	$a2, 0($sp)	#restore a2
	sw	$v0, 0($sp)	#store v0
	jal	getColor	#v1 has color
	lw	$v0, 0($sp)
	sw	$v1, 0($v0)	#make dot
	lw	$ra, 4($sp)
	jr	$ra

getColor:
	#Procedure: getColor
	#gets the color from color table
	#Input: $a2 color number [0-7]
	#Output: $v1 is color data source
	la	$t0, ColorTable
	sll	$a2, $a2, 2
	addu	$a2, $a2, $t0		#add together
	lw	$v1, 0($a2)		#load color table location into $v1
	jr	$ra
	
getRow:
	#Procedure: getColor
	#gets the color from color table
	#Input: $a2 color number [0-5]
	#Output: $v1 is color data source
	la	$t0, rowTable
	sll	$a2, $a2, 2
	addu	$a2, $a2, $t0		#add together
	lw	$v1, 0($a2)		#load row table location into $v1
	jr	$ra
	
getColumn:
	#Procedure: getColor
	#gets the color from color table
	#Input: $a2 color number [0-6]
	#Output: $v1 is color data source
	la	$t0, columnTable
	sll	$a2, $a2, 2
	addu	$a2, $a2, $t0		#add together
	lw	$v1, 0($a2)		#load row table location into $v1
	jr	$ra
	
calcAddr:
	#Procedure: calcAddr
	#abstracts X & Y coordinates for simplicity
	#Input: $a0 = x coordinate
	#Input: $a1 = y coordinate
	#Returns: $v0 = memory address
	#method: v0 = base + $a0 * 4 + $a1 * 256 * 4
	
	lw	$v0, Base		#load base zero
	mul	$a0, $a0, 0x04		#multiply x coordinate by 4 for word
	add	$v0, $v0, $a0		#add x coordinate to base
	mul	$a1, $a1, 0x100		#multply y coordinate by 256 for vertical
	mul	$a1, $a1, 0x04		#multiply y coordinate by 4 for word
	addu	$v0, $v0, $a1		#add y coordinate to base
	jr	$ra 	

clearBoard:
	#Procedure: clearBoard
	#clears the board
	
	la	$sp, rastack_end	#store $ra
	addiu	$sp, $sp, -16
	sw	$ra, 0($sp)
	
	li	$a0, 0	#set x 0
	li	$a1, 0	#set y 0
	li	$a2, 0	#set black
	li	$a3, 255
	jal	drawBox

	la	$sp, rastack_end	#store $ra
	addiu	$sp, $sp, -16
	lw	$ra, 0($sp)	
	jr	$ra

drawBox:
	#Procedure: drawBox
	#draws a box of the given color
	#Input $a0 x coord
	#Input $a1 y coord
	#Input $a2 color
	#Input $a3 size

	move	$s0, $sp		#backup sp	
	la	$sp, stack_end
	addiu	$sp, $sp, -24		#create stack frame
	sw	$ra, 20($sp)		#save ra
	move 	$t0, $a3		#copy $a3 -> temp register $t0
	
	boxLoop:

		sw	$a0, 0($sp)
		sw	$a1, 4($sp)
		sw	$a2, 8($sp)
		sw	$a3, 12($sp)
		sw	$t0, 16($sp)
		jal	horzLine
		la	$sp, stack_end
		addiu	$sp, $sp, -24
		lw	$a0, 0($sp)
		lw	$a1, 4($sp)
		lw	$a2, 8($sp)
		lw	$a3, 12($sp)
		lw	$t0, 16($sp)
		addiu	$a1, $a1, 1		#increment y
		addiu	$t0, $t0, -1		#decrement counter
		bne	$t0, $0, boxLoop	#loop as long as some left
		lw	$ra, 20($sp)
		move	$sp, $s0		#restore sp
		jr	$ra

horzLine:
	#Procedure: horzLine
	#draws a horizontal line
	#$a0 = x coord
	#$a1 = y coord
	#$a2 = color number 
	#$a3 = length of line 1-256
		
	la	$sp, stack_end
	addiu	$sp, $sp, -44		#create stack frame
	
	sw	$ra, 16($sp)		#save ra

	horzLoop:
		sw	$a0, 0($sp)
		sw	$a1, 4($sp)
		sw	$a2, 8($sp)
		sw	$a3, 12($sp)
		jal	drawDot
		la	$sp, stack_end
		addiu	$sp, $sp, -44		#create stack frame		
		lw	$a0, 0($sp)
		lw	$a1, 4($sp)
		lw	$a2, 8($sp)
		lw	$a3, 12($sp)		
		addiu	$a0, $a0, 1			#increment x coordinate ($a0)
		addiu	$a3, $a3, -1			#decrement line left ($a3)
		bne	$a3, $0, horzLoop
		lw	$ra, 16($sp)			#restore ra
		jr	$ra

vertLine:
	#Procedure: horzLine
	#draws a horizontal line
	#$a0 = x coord
	#$a1 = y coord
	#$a2 = color number 
	#$a3 = length of line 1-256
		
	la	$sp, stack_end
	addiu	$sp, $sp, -44		#create stack frame
	
	sw	$ra, 16($sp)		#save ra

	vertLoop:
		sw	$a0, 0($sp)
		sw	$a1, 4($sp)
		sw	$a2, 8($sp)
		sw	$a3, 12($sp)
		jal	drawDot
		la	$sp, stack_end
		addiu	$sp, $sp, -44		#create stack frame		
		lw	$a0, 0($sp)
		lw	$a1, 4($sp)
		lw	$a2, 8($sp)
		lw	$a3, 12($sp)		
		addiu	$a1, $a1, 1			#increment y coordinate ($a1)
		addiu	$a3, $a3, -1			#decrement line left ($a3)
		bne	$a3, $0, vertLoop
		lw	$ra, 16($sp)			#restore ra
		jr	$ra	

drawCircle:
	#Procedure: drawCircle
	#draws a circle of the given color
	#Input $a0 left center x coord
	#Input $a1 left center y coord
	#Input $a2 color
	la	$t0, CircleLengthTable
	la	$sp, stack_end
	addiu	$sp, $sp, -24		#create stack frame
	sw	$ra, 20($sp)		#save ra
	li	$t1, 0	
	move 	$s1, $a0
	move	$s2, $a1
	j	drawTop

	
	drawTop:

		lw	$a3, 0($t0)		#load size of line to draw
		addiu	$t0, $t0, 4		#add offset to line length pointer
		sub	$t3, $a3, 24		#subtract length from full size
		mul	$t3, $t3, -1
		div	$t3, $t3, 2		#divide that value by two
		move	$a0, $s1		#move base x to $a0
		addu	$a0, $a0, $t3		#shift base x pos by offset		
		
		#backup registers
		sw	$a0, 0($sp)
		sw	$a1, 4($sp)
		sw	$a2, 8($sp)
		sw	$a3, 12($sp)
		sw	$t0, 16($sp)
		jal	horzLine	#call line
		#restore registers
		la	$sp, stack_end
		addiu	$sp, $sp, -24
		lw	$a0, 0($sp)
		lw	$a1, 4($sp)
		lw	$a2, 8($sp)
		lw	$a3, 12($sp)
		lw	$t0, 16($sp)
		addiu	$t1, $t1, 1		#increment offset
		addi	$a1, $a1, -1		#decrement y


		bne	$t1, 11, drawTop	#loop as long as some left
		li	$t3, 0			#reset $t3
		la	$t0, CircleLengthTable
		li	$t1, 0
		move	$a0, $s1		#move base x
		move	$a1, $s2		#move base y
		j	drawBottom
	
	
	drawBottom:
		lw	$a3, 0($t0)		#load size of line to draw
		addiu	$t0, $t0, 4		#add offset to line length pointer
		sub	$t3, $a3, 24		#subtract length from full size
		mul	$t3, $t3, -1
		div	$t3, $t3, 2		#divide that value by two
		move	$a0, $s1		#move base x to $a0
		addu	$a0, $a0, $t3		#shift base x pos by offset
		#backup registers
		sw	$a0, 0($sp)
		sw	$a1, 4($sp)
		sw	$a2, 8($sp)
		sw	$a3, 12($sp)
		sw	$t0, 16($sp)
		jal	horzLine		#call line
		#restore registers
		la	$sp, stack_end
		addiu	$sp, $sp, -24
		lw	$a0, 0($sp)
		lw	$a1, 4($sp)
		lw	$a2, 8($sp)
		lw	$a3, 12($sp)
		lw	$t0, 16($sp)
		addiu	$t1, $t1, 1		#increment offset
		addi	$a1, $a1, 1		#increment y

		bne	$t1, 11, drawBottom	#loop as long as some left
		li	$t3, 0	
		lw	$ra, 20($sp)
		jr	$ra	
		
		
# OutText: display ascii characters on the bit mapped display
# $a0 = horizontal pixel co-ordinate (0-255)
# $a1 = vertical pixel co-ordinate (0-255)
# $a2 = pointer to asciiz text (to be displayed)
OutText:
        addiu   $sp, $sp, -24
        sw      $ra, 20($sp)
        li      $t8, 1          # line number in the digit array (1-12)
_text1:
        la      $t9, 0x10040000 # get the memory start address
        sll     $t0, $a0, 2     # assumes mars was configured as 256 x 256
        addu    $t9, $t9, $t0   # and 1 pixel width, 1 pixel height
        sll     $t0, $a1, 10    # (a0 * 4) + (a1 * 4 * 256)
        addu    $t9, $t9, $t0   # t9 = memory address for this pixel

        move    $t2, $a2        # t2 = pointer to the text string
_text2:
        lb      $t0, 0($t2)     # character to be displayed
        addiu   $t2, $t2, 1     # last character is a null
        beq     $t0, $zero, _text9

        la      $t3, DigitTable # find the character in the table
_text3:
        lb      $t4, 0($t3)     # get an entry from the table
        beq     $t4, $t0, _text4
        beq     $t4, $zero, _text4
        addiu   $t3, $t3, 13    # go to the next entry in the table
        j       _text3
_text4:
        addu    $t3, $t3, $t8   # t8 is the line number
        lb      $t4, 0($t3)     # bit map to be displayed

        sw      $zero, 0($t9)   # first pixel is black
        addiu   $t9, $t9, 4

        li      $t5, 8          # 8 bits to go out
_text5:
        la      $t7, Colors
        lw      $t7, 0($t7)     # assume black
        andi    $t6, $t4, 0x80  # mask out the bit (0=black, 1=white)
        beq     $t6, $zero, _text6
        la      $t7, Colors     # else it is white
        lw      $t7, 4($t7)
_text6:
        sw      $t7, 0($t9)     # write the pixel color
        addiu   $t9, $t9, 4     # go to the next memory position
        sll     $t4, $t4, 1     # and line number
        addiu   $t5, $t5, -1    # and decrement down (8,7,...0)
        bne     $t5, $zero, _text5

        sw      $zero, 0($t9)   # last pixel is black
        addiu   $t9, $t9, 4
        j       _text2          # go get another character

_text9:
        addiu   $a1, $a1, 1     # advance to the next line
        addiu   $t8, $t8, 1     # increment the digit array offset (1-12)
        bne     $t8, 13, _text1

        lw      $ra, 20($sp)
        addiu   $sp, $sp, 24
        jr      $ra


gameOver:
	li	$v0, 4
	la	$a0, tie
	syscall
	
	li	$v0, 10
	syscall
