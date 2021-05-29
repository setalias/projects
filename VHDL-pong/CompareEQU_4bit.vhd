----------------------------------------------------------------------------
-- Entity:        CompareEQU_4bit
-- Written By:    Sandy Lee
-- Date Created:  27 April 2020
-- Description:   VHDL model of an 8-bit equality comparitor
--
-- Dependencies:
--
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity CompareEQU_4bit is
    port (
        A   : in  STD_LOGIC_VECTOR(3 downto 0);
        B   : in  STD_LOGIC_VECTOR(3 downto 0);
        EQU : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of CompareEQU_4bit is

-- internal signals
    signal xor_int :   STD_LOGIC_VECTOR(3 downto 0);
    
begin

	xor_int(3 downto 0) <= NOT (A(3 downto 0) XOR B(3 downto 0));
	EQU <= xor_int(3) AND xor_int(2) AND xor_int(1) AND xor_int(0);
	
end architecture;
