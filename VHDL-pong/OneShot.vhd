----------------------------------------------------------------------------
-- Entity:        OneShot
-- Written By:    Sandy Lee
-- Date Created:  29 Mar 2020
-- Description:   VHDL model of a one-shot (rising edge detector)
--
-- Revision History (date, initials, description):
--
-- Dependencies:
--   (none)
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity OneShot is
    port (
        D     : in  STD_LOGIC;
        CLK   : in  STD_LOGIC;
        Q     : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of OneShot is

component DFF is
    port (
        D   : in  STD_LOGIC;
        CLK : in  STD_LOGIC;
        Q   : out STD_LOGIC
    );
end component; 

-- internal signals

signal  D_int   :   STD_LOGIC;
signal  Q_int   :   STD_LOGIC;


begin


latch0  :   DFF
    port map (
        D   =>  D_int,
        CLK =>  CLK,
        Q   =>  Q_int
    );
    
D_int   <=  (NOT D);
Q       <=  (Q_int AND D);

end architecture;
