----------------------------------------------------------------------------
-- Description:   VHDL model of a D flip-flop
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity DFF is
    port (
        D    : in  STD_LOGIC;
        CLK  : in  STD_LOGIC;
        Q    : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------

architecture Behavioral of DFF is

begin

    process (CLK)
    begin
        if rising_edge(CLK) then
            Q <= D;
        end if;
    end process;

end architecture;
