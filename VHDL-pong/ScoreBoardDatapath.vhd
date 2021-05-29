----------------------------------------------------------------------------
-- Entity:        ScoreBoardDatapath
-- Written By:    Sandy Lee
-- Date Created:  27 Apr 20
-- Description:   VHDL model of the score board datapath for a Pong game
--
-- Dependencies:
--  CompareEQU_4bit
--  Reg_LOAD_CLR_8bit
--  Mux2to1_8bit
--  Adder_8bit
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity ScoreBoardDatapath is
    port (
        LOADP1  : in  STD_LOGIC;
        LOADP2  : in  STD_LOGIC;
        P1ORP2  : in  STD_LOGIC;
        ADD1OR6 : in  STD_LOGIC;
        CLK     : in  STD_LOGIC;
        RESET   : in  STD_LOGIC;
        P1SCORE : out STD_LOGIC_VECTOR(7 downto 0);
        P2SCORE : out STD_LOGIC_VECTOR(7 downto 0);
        EQU10   : out STD_LOGIC
    );
end entity;
----------------------------------------------------------------------------
architecture Structural of ScoreBoardDatapath is

    component Reg_LOAD_CLR_8bit is
        port (
            D    : in  STD_LOGIC_VECTOR(7 downto 0);
            CLK  : in  STD_LOGIC;
            LOAD : in  STD_LOGIC;
            CLR  : in  STD_LOGIC;
            Q    : out STD_LOGIC_VECTOR(7 downto 0)
        );
    end component;
    
    component Mux2to1_8bit is
        port (
            D0  : in  STD_LOGIC_VECTOR (7 downto 0);
            D1  : in  STD_LOGIC_VECTOR (7 downto 0);
            SEL : in  STD_LOGIC;
            Y   : out STD_LOGIC_VECTOR (7 downto 0)
        );
    end component;
    
    component Adder_8bit is
        port (
            A   : in  STD_LOGIC_VECTOR (7 downto 0);
            B   : in  STD_LOGIC_VECTOR (7 downto 0);
            SUM : out STD_LOGIC_VECTOR (7 downto 0)
        );
    end component;
    
    component CompareEQU_4bit is
        port (
            A   : in  STD_LOGIC_VECTOR(3 downto 0);
            B   : in  STD_LOGIC_VECTOR(3 downto 0);
            EQU : out STD_LOGIC
        );
    end component;

-- internal signals
    signal P1SCORE_int  :   STD_LOGIC_VECTOR(7 downto 0);
    signal P2SCORE_int  :   STD_LOGIC_VECTOR(7 downto 0);
    signal Mux0_int     :   STD_LOGIC_VECTOR(7 downto 0);
    signal Mux1_int     :   STD_LOGIC_VECTOR(7 downto 0);         
    signal Adder_SUM    :   STD_LOGIC_VECTOR(7 downto 0);          
    
begin

P2SCORE(7 downto 0) <=  P2SCORE_int;
P1SCORE(7 downto 0) <=  P1SCORE_int;



    Reg0    :   Reg_LOAD_CLR_8bit
        port map (
            D       =>  Adder_SUM(7 downto 0),
            CLK     =>  CLK,
            LOAD    =>  LOADP1,
            CLR     =>  RESET,
            Q       =>  P1SCORE_int(7 downto 0)
        );
    Reg1    :   Reg_LOAD_CLR_8bit
        port map (
            D       =>  Adder_SUM(7 downto 0),
            CLK     =>  CLK,
            LOAD    =>  LOADP2,
            CLR     =>  RESET,
            Q       =>  P2SCORE_int(7 downto 0)
        );
    
    Mux0    :   Mux2to1_8bit 
        port map (
            D0      =>  P1SCORE_int(7 downto 0),
            D1      =>  P2SCORE_int(7 downto 0),
            SEL     =>  P1ORP2,
            Y       =>  Mux0_int(7 downto 0)
        );
    
    Mux1    :   Mux2to1_8bit 
        port map (
            D0      =>  "00000001",
            D1      =>  "00000110",
            SEL     =>  ADD1OR6,
            Y       =>  Mux1_int(7 downto 0)
        );
    
    
    Adder0  :   Adder_8bit
        port map (
            A       =>  Mux1_int(7 downto 0),
            B       =>  Mux0_int(7 downto 0),
            SUM     =>  Adder_SUM(7 downto 0)
        );
    
    Compare0    :   CompareEQU_4bit
        port map (
            A       =>  Mux0_int(3 downto 0),
            B       =>  "1010",
            EQU     =>  EQU10
        );
    



end architecture;
