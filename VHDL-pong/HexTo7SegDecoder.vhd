----------------------------------------------------------------------------
-- Entity:        HexTo7SegDecoder
-- Written By:    Sandy Lee
-- Date Created:  22Feb2020
-- Description:   VHDL model of a hex digit to 7-segment display decoder
--
-- Revision History (22FEB2020, SAL, v1):
--
-- Dependencies:
--   (none)
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity HexTo7SegDecoder is
    port (
        HEX     : in  STD_LOGIC_VECTOR(3 downto 0);
        SEGMENT : out STD_LOGIC_VECTOR(0 to 6)              
    );
    
end entity;
----------------------------------------------------------------------------

architecture Structural of HexTo7SegDecoder is
       
        -- Input aliases
        alias S3 : STD_LOGIC is HEX(3);
        alias S2 : STD_LOGIC is HEX(2);
        alias S1 : STD_LOGIC is HEX(1);
        alias S0 : STD_LOGIC is HEX(0);
        
        -- Output aliases
        alias A : STD_LOGIC is SEGMENT(0);
        alias B : STD_LOGIC is SEGMENT(1);
        alias C : STD_LOGIC is SEGMENT(2);
        alias D : STD_LOGIC is SEGMENT(3);
        alias E : STD_LOGIC is SEGMENT(4);
        alias F : STD_LOGIC is SEGMENT(5);
        alias G : STD_LOGIC is SEGMENT(6);
        
        
        -- Internal signals (minterms)
        signal m0 : STD_LOGIC;
        signal m1 : STD_LOGIC;
        signal m2 : STD_LOGIC;
        signal m3 : STD_LOGIC;
        signal m4 : STD_LOGIC;
        signal m5 : STD_LOGIC;
        signal m6 : STD_LOGIC;
        signal m7 : STD_LOGIC;
        signal m8 : STD_LOGIC;
        signal m9 : STD_LOGIC;
        signal m10 : STD_LOGIC;
        signal m11 : STD_LOGIC;
        signal m12 : STD_LOGIC;
        signal m13 : STD_LOGIC;
        signal m14 : STD_LOGIC;
        signal m15 : STD_LOGIC;
        
    begin
           
        -- logic for hex counting
        m0 <= not S3 and not S2 and not S1 and not S0;  -- A'B'C'D' 0
        m1 <= not S3 and not S2 and not S1 and S0;      -- A'B'C'D  1
        m2 <= not S3 and not S2 and S1 and not S0;      -- A'B'CD'  2
        m3 <= not S3 and not S2 and S1 and S0;          -- A'B'CD   3
        m4 <= not S3 and S2 and not S1 and not S0;      -- A'BC'D'  4
        m5 <= not S3 and S2 and not S1 and S0;          -- A'BC'D   5
        m6 <= not S3 and S2 and S1 and not S0;          -- A'BCD'   6
        m7 <= not S3 and S2 and S1 and S0;              -- A'BCD    7
        m8 <= S3 and not S2 and not S1 and not S0;      -- AB'C'D'  8
        m9 <= S3 and not S2 and not S1 and S0;          -- AB'C'D   9   
        m10 <= S3 and not S2 and S1 and not S0;         -- AB'CD'   A
        m11 <= S3 and not S2 and S1 and S0;             -- AB'CD    b
        m12 <= S3 and S2 and not S1 and not S0;         -- ABC'D'   C
        m13 <= S3 and S2 and not S1 and S0;             -- ABC'D    d
        m14 <= S3 and S2 and S1 and not S0;             -- ABCD'    E
        m15 <= S3 and S2 and S1 and S0;                 -- ABCD     F
       
        -- turn segments "on" based on pattern desired
        A <= m1 or m4 or m11 or m13;
        B <= m5 or m6 or m11 or m12 or m14 or m15;
        C <= m2 or m12 or m14 or m15;
        D <= m1 or m4 or m7 or m9 or m10 or m15;
        E <= m1 or m3 or m4 or m5 or m7 or m9;
        F <= m1 or m2 or m3 or m7 or m13;
        G <= m0 or m1 or m7 or m12;
        
end architecture;
