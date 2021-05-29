----------------------------------------------------------------------------
-- Entity:        FullAdder
-- Written By:    Sandy Lee
-- Date Created:  07 Mar 2020
-- Description:   VHDL model of a full adder.  Adds three bits, producing
--   a sum and a carry-out bit
--
-- Dependencies:
--   (none)
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity FullAdder is
    port (
        A  : in  STD_LOGIC;
        B  : in  STD_LOGIC;
        Ci : in  STD_LOGIC;
        Co : out STD_LOGIC;
        S  : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture DataFlow of FullAdder is

    -- Internal signals
    signal wire1 : STD_LOGIC;
    signal wire2 : STD_LOGIC;
    signal wire3 : STD_LOGIC;

begin

    -- Logic for internal signals
    wire1   <= A and B;
    wire2   <= A xor B;
    wire3   <= wire2 and Ci;
    
    -- Logic for full adder outputs
    Co      <= wire1 or wire3;
    S       <= wire2 xor Ci;
	
end DataFlow;
