----------------------------------------------------------------------------
-- Entity:        DFF_EN_RESET
-- Written By:    Sandy Lee
-- Date Created:  29Feb20
-- Description:   VHDL model of a D flip-flop with enable and reset
--
-- Revision History (1Mar20, SAL, v2):
--
-- Dependencies:
--   DFF
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity DFF_EN_RESET is
    port (
        D     : in  STD_LOGIC;
        CLK   : in  STD_LOGIC;
        EN    : in  STD_LOGIC;
        RESET : in  STD_LOGIC;
        Q     : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of DFF_EN_RESET is
    
    component Mux2to1 is
        port (
            D0  : in  STD_LOGIC;
            D1  : in  STD_LOGIC;
            SEL : in  STD_LOGIC;
            Y   : out STD_LOGIC
        );
    end component;

    component DFF is
        port (
            D    : in  STD_LOGIC;
            CLK  : in  STD_LOGIC;
            Q    : out STD_LOGIC
        );
    end component;

    signal n1_int   : STD_LOGIC;
    signal n2_int   : STD_LOGIC;
    signal Q_int    : STD_LOGIC;

begin

    n2_int <= (n1_int AND NOT RESET);
         
    mux: Mux2to1 
        port map (
           D0      => Q_int,
           D1      => D,
           SEL     => EN,
           Y       => n1_int
       );
     
    latch1: DFF 
        port map (
            D       => n2_int,
            CLK     => CLK,
            Q       => Q_int
        );
        
        Q <= Q_int;
        
end architecture;
