----------------------------------------------------------------------------
-- Entity:        Decoder2to4_ALO
-- Written By:    Sandy Lee
-- Date Created:  22 Feb 2020
-- Description:   VHDL model of a 2 to 4 decoder with active low outputs
--
-- Revision History (22Feb2020, SAL, v1):
--
-- Dependencies:
--   (none)
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity Decoder2to4_ALO is
    port (
        A : in  STD_LOGIC_VECTOR(1 downto 0);
        Y : out STD_LOGIC_VECTOR(3 downto 0)
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of Decoder2to4_ALO is

begin

    Y(3) <= not (A(1) and A(0));
    Y(2) <= not (A(1) and not A(0));
    Y(1) <= not (not A(1) and A(0));
    Y(0) <= not (not A(1) and not A(0));

end architecture;
