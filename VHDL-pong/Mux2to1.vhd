----------------------------------------------------------------------------
-- Description:   VHDL model of a 2 to 1 multiplexer
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity Mux2to1 is
    port (
        D0  : in  STD_LOGIC;
        D1  : in  STD_LOGIC;
        SEL : in  STD_LOGIC;
        Y   : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of Mux2to1 is

begin

    Y <= (not SEL and D0) or (SEL and D1);

end architecture;
