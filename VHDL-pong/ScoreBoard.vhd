----------------------------------------------------------------------------
-- Description:   VHDL model of the score board for a Pong game
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity ScoreBoard is
    port (
        P1POINT : in  STD_LOGIC;
        P2POINT : in  STD_LOGIC;
        CLK     : in  STD_LOGIC;
        RESET   : in  STD_LOGIC;
        P1SCORE : out STD_LOGIC_VECTOR(7 downto 0);
        P2SCORE : out STD_LOGIC_VECTOR(7 downto 0)
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of ScoreBoard is

    component ScoreBoardFSM is
        port (
            P1POINT : in  STD_LOGIC;
            P2POINT : in  STD_LOGIC;
            EQU10   : in  STD_LOGIC;
            CLK     : in  STD_LOGIC;
            RESET   : in  STD_LOGIC;
            LOADP1  : out STD_LOGIC;
            LOADP2  : out STD_LOGIC;
            P1ORP2  : out STD_LOGIC;
            ADD1OR6 : out STD_LOGIC
        );
    end component;
    
    component ScoreBoardDatapath is
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
    end component;
        
-- internal signals
    signal  LOADP1_int  :   STD_LOGIC;
    signal  LOADP2_int  :   STD_LOGIC;
    signal  P1ORP2_int  :   STD_LOGIC;
    signal  ADD1OR6_int :   STD_LOGIC;
    signal  EQU10_int   :   STD_LOGIC;   

begin

    Scoreboard0 :   ScoreBoardFSM
        port map (
            P1POINT =>  P1POINT,
            P2POINT =>  P2POINT,
            EQU10   =>  EQU10_int,
            CLK     =>  CLK,
            RESET   =>  RESET,
            LOADP1  =>  LOADP1_int,
            LOADP2  =>  LOADP2_int,
            P1ORP2  =>  P1ORP2_int,
            ADD1OR6 =>  ADD1OR6_int
        );
    
    Scoreboard1 :   ScoreBoardDatapath 
        port map (
            LOADP1  =>  LOADP1_int,
            LOADP2  =>  LOADP2_int,
            P1ORP2  =>  P1ORP2_int,
            ADD1OR6 =>  ADD1OR6_int,
            CLK     =>  CLK,
            RESET   =>  RESET,
            P1SCORE =>  P1SCORE(7 downto 0),
            P2SCORE =>  P2SCORE(7 downto 0),
            EQU10   =>  EQU10_int
        );
    



end architecture;
