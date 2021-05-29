--------------------------------------------------------------------------------
-- Entity:        Lab15b_skl5511
-- Written By:    Sandy Lee
-- Date Created:  27 Apr 20
-- Description:   CMPEN 270 Lab #15a starting VHDL
-- Dependencies:
--
--
--------------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

--------------------------------------------------------------------------------
entity Lab15b_skl5511 is
    port (
		SWITCH  : in  STD_LOGIC_VECTOR(15 downto 0);
		BTNL    : in  STD_LOGIC;
		BTNR    : in  STD_LOGIC;
		BTNU    : in  STD_LOGIC;
		BTND    : in  STD_LOGIC;
		CLK     : in  STD_LOGIC;
		LED     : out STD_LOGIC_VECTOR(15 downto 0);
		SEGMENT : out STD_LOGIC_VECTOR(0 to 6);
        ANODE   : out STD_LOGIC_VECTOR(3 downto 0)
	);
end entity;
--------------------------------------------------------------------------------

architecture Structural of Lab15b_skl5511 is

    component ControlFSM is
        port (
            BTNU      : in  STD_LOGIC;
            BTNL      : in  STD_LOGIC;
            BTNR      : in  STD_LOGIC;
            POS       : in  STD_LOGIC_VECTOR(15 downto 0);
            CLK       : in  STD_LOGIC;
            RESET     : in  STD_LOGIC;
            MOVELEFT  : out STD_LOGIC;
            MOVERIGHT : out STD_LOGIC;
            P1RTS     : out STD_LOGIC;
            P1POINT   : out STD_LOGIC;
            P2RTS     : out STD_LOGIC;
            P2POINT   : out STD_LOGIC
      );
    end component;

    component DisplayFSM is
        port (
            MOVELEFT  : in  STD_LOGIC;
            MOVERIGHT : in  STD_LOGIC;
            P1RTS     : in  STD_LOGIC;
            P1POINT   : in  STD_LOGIC;
            P2RTS     : in  STD_LOGIC;
            P2POINT   : in  STD_LOGIC;
            CLK       : in  STD_LOGIC;
            RESET     : in  STD_LOGIC;
            POS       : out STD_LOGIC_VECTOR(15 downto 0)
        );
    end component;

    component Synch is
        port (
            D     : in  STD_LOGIC;
            CLK   : in  STD_LOGIC;
            Q     : out STD_LOGIC
        );
    end component;
    
    component Debounce is
        port (
            D     : in  STD_LOGIC;
            CLK   : in  STD_LOGIC;
            PULSE : in  STD_LOGIC;
            Q     : out STD_LOGIC
        );
    end component;
    
    component OneShot is
        port (
            D     : in  STD_LOGIC;
            CLK   : in  STD_LOGIC;
            Q     : out STD_LOGIC
        );
    end component;
    
    component PulseGenerator_1ms is
        port (
            CLK    : in  STD_LOGIC;
            PULSE  : out STD_LOGIC
        );
    end component;
    
    component PulseGenerator_125ms is
        port (
            CLK    : in  STD_LOGIC;
            PULSE  : out STD_LOGIC
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
    
    component ScoreBoard is
        port (
            P1POINT : in  STD_LOGIC;
            P2POINT : in  STD_LOGIC;
            CLK     : in  STD_LOGIC;
            RESET   : in  STD_LOGIC;
            P1SCORE : out STD_LOGIC_VECTOR(7 downto 0);
            P2SCORE : out STD_LOGIC_VECTOR(7 downto 0)
        );
    end component;    

    component WordTo4DigitDisplayDriver is
        port (
            WORD    : in  STD_LOGIC_VECTOR(15 downto 0);
            PULSE   : in  STD_LOGIC;
            CLK     : in  STD_LOGIC;
            SEGMENT : out STD_LOGIC_VECTOR(0 to 6);
            ANODE   : out STD_LOGIC_VECTOR(3 downto 0)
        );
    end component;

-- internal signals
    signal  pulse_125ms     :   STD_LOGIC;
    signal  pulse_1ms       :   STD_LOGIC;
    signal  synch_Q         :   STD_LOGIC_VECTOR(0 to 1);
    signal  debounce_Q      :   STD_LOGIC_VECTOR(0 to 1);
    signal  oneshot_Q       :   STD_LOGIC_VECTOR(0 to 1);     
    signal  moveleft_out    :   STD_LOGIC;
    signal  moveright_out   :   STD_LOGIC;
    signal  moveleft_in     :   STD_LOGIC;
    signal  moveright_in    :   STD_LOGIC;
    signal  p1rts_int       :   STD_LOGIC;
    signal  p1point_int     :   STD_LOGIC;
    signal  p2rts_int       :   STD_LOGIC;
    signal  p2point_int     :   STD_LOGIC;
    signal  display_pos     :   STD_LOGIC_VECTOR(15 downto 0);
    signal  displayWord     :   STD_LOGIC_VECTOR(15 downto 0);
    
    
    
    
    
begin

    moveleft_in         <= moveleft_out and pulse_125ms;
    moveright_in        <= moveright_out and pulse_125ms;
    LED(15 downto 0)    <=  display_pos(15 downto 0);


    FSM0    :   ControlFSM
        port map (
            BTNU      =>    BTNU,
            BTNL      =>    oneshot_Q(0),
            BTNR      =>    oneshot_Q(1),
            POS       =>    display_pos(15 downto 0),
            CLK       =>    CLK,
            RESET     =>    BTND,
            MOVELEFT  =>    moveleft_out,
            MOVERIGHT =>    moveright_out,
            P1RTS     =>    p1rts_int,
            P1POINT   =>    p1point_int,
            P2RTS     =>    p2rts_int,
            P2POINT   =>    p2point_int
        );

    FSM1    :   DisplayFSM
        port map (
            MOVELEFT  =>    moveleft_in,     
            MOVERIGHT =>    moveright_in,
            P1RTS     =>    p1rts_int,
            P1POINT   =>    p1point_int,
            P2RTS     =>    p2rts_int,
            P2POINT   =>    p2point_int,
            CLK       =>    CLK,
            RESET     =>    BTND,
            POS       =>    display_pos(15 downto 0)
        );
    
    Synch0  :   Synch
        port map (
            D     =>    BTNL,    
            CLK   =>    CLK,
            Q     =>    synch_Q(0)
        );
        
    Synch1  :   Synch
        port map (
            D     =>    BTNR,
            CLK   =>    CLK,
            Q     =>    synch_Q(1)
        );
    
    Debounce0   :   Debounce 
        port map (
            D     =>    synch_Q(0),
            CLK   =>    CLK,
            PULSE =>    pulse_1ms,
            Q     =>    debounce_Q(0)
        );
    Debounce1   :   Debounce 
        port map (
            D     =>    synch_Q(1),
            CLK   =>    CLK,
            PULSE =>    pulse_1ms,
            Q     =>    debounce_Q(1)
        );
            
    OneShot0    :   OneShot
        port map (
            D     =>    debounce_Q(0),    
            CLK   =>    CLK,
            Q     =>    oneshot_Q(0)
        );
    
    OneShot1    :   OneShot
        port map (
            D     =>    debounce_Q(1),    
            CLK   =>    CLK,
            Q     =>    oneshot_Q(1)
        );

    Pulse0  :   PulseGenerator_1ms
        port map (
            CLK    =>   CLK,
            PULSE  =>   pulse_1ms
        );
    
    Pulse1  :   PulseGenerator_125ms
        port map (
            CLK    =>   CLK,
            PULSE  =>   pulse_125ms
        );
    
    Scoreboard0 :   ScoreBoard
        port map (
            P1POINT => p1point_int,
            P2POINT => p2point_int,
            CLK     => CLK,
            RESET   => BTND,
            P1SCORE => displayWord(15 downto 8),
            P2SCORE => displayWord(7 downto 0)
        );
        
    Scoreboard1 :   WordTo4DigitDisplayDriver
        port map (
            WORD    =>  displayWord(15 downto 0),
            PULSE   =>  pulse_1ms,
            CLK     =>  CLK,
            SEGMENT =>  SEGMENT(0 to 6),
            ANODE   =>  ANODE(3 downto 0)
        );
    
    
end architecture;
