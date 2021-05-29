----------------------------------------------------------------------------
-- Entity:        Adder_2bit
-- Written By:    Sandy Lee
-- Date Created:  07 Mar 2020
-- Description:   VHDL model of a 2-bit ripple-carry adder
--
-- Dependencies:
-- FullAdder
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity Adder_2bit is
    port (
        A  : in  STD_LOGIC_VECTOR (1 downto 0);
        B  : in  STD_LOGIC_VECTOR (1 downto 0);
        Ci : in  STD_LOGIC;
        Co : out STD_LOGIC;
        S  : out STD_LOGIC_VECTOR (1 downto 0)
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of Adder_2bit is

    component FullAdder is
        port (
            A   : in  STD_LOGIC;
            B   : in  STD_LOGIC;
            Ci  : in  STD_LOGIC;
            Co  : out STD_LOGIC;
            S   : out STD_LOGIC
        );
      end component;

signal carry    : STD_LOGIC_VECTOR (2 downto 0);

begin

-- internal carry signals
    carry(0)    <= Ci;
    Co          <= carry(2);

-- instantiate full adders
    FA0: FullAdder port map(
        A   => A(0), 
        B   => B(0),
        Ci  => carry(0),
        Co  => carry(1),
        S   => S(0)
    );
    
    FA1: FullAdder port map(
        A   => A(1), 
        B   => B(1),
        Ci  => carry(1),
        Co  => carry(2),
        S   => S(1)
    );
end architecture;

