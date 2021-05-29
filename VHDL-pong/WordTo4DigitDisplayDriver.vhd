----------------------------------------------------------------------------
-- Entity:        WordTo4DigitDisplayDriver
-- Written By:    Sandy Lee
-- Date Created:  7 Mar 2020
-- Description:   VHDL model of a word to 4-digit 7-segment display driver
--
-- Dependencies:
--   Mux4to1_4bit
--   Counter_2bit
--   HexTo7SegDecoder
--   Decoder2to4_ALO
----------------------------------------------------------------------------
library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;

----------------------------------------------------------------------------
entity WordTo4DigitDisplayDriver is
    port (
        WORD    : in  STD_LOGIC_VECTOR(15 downto 0);
        PULSE   : in  STD_LOGIC;
        CLK     : in  STD_LOGIC;
        SEGMENT : out STD_LOGIC_VECTOR(0 to 6);
		ANODE   : out STD_LOGIC_VECTOR(3 downto 0)
    );
end entity;
----------------------------------------------------------------------------

architecture Structural of WordTo4DigitDisplayDriver is

    component Mux4to1_4bit is
        port (
            D0  : in  STD_LOGIC_VECTOR(3 downto 0);
            D1  : in  STD_LOGIC_VECTOR(3 downto 0);
            D2  : in  STD_LOGIC_VECTOR(3 downto 0);
            D3  : in  STD_LOGIC_VECTOR(3 downto 0);
            SEL : in  STD_LOGIC_VECTOR(1 downto 0);
            Y   : out STD_LOGIC_VECTOR(3 downto 0)
        );
    end component;
    
    
    component Counter_2bit is
        port (
            EN   : in  STD_LOGIC;
            CLK  : in  STD_LOGIC;
            CLR  : in  STD_LOGIC;
            ROLL : out STD_LOGIC;
            Q    : out STD_LOGIC_VECTOR(1 downto 0)
        );
    end component;
    
    component HexTo7SegDecoder is
        port (
            HEX     : in  STD_LOGIC_VECTOR(3 downto 0);
            SEGMENT : out STD_LOGIC_VECTOR(0 to 6)              
        );
    end component;
        
    component Decoder2to4_ALO is
        port (
            A : in  STD_LOGIC_VECTOR(1 downto 0);
            Y : out STD_LOGIC_VECTOR(3 downto 0)    
        );
    end component;
    
-- internal signals
    signal curDigit : STD_LOGIC_VECTOR(3 downto 0);
    signal digitSelect  :   STD_LOGIC_VECTOR (1 downto 0);
    
begin

        Mux1: Mux4to1_4bit
        port map (
            D0  =>  WORD(3 downto 0),   
            D1  =>  WORD(7 downto 4),
            D2  =>  WORD(11 downto 8),
            D3  =>  WORD(15 downto 12),
            SEL =>  digitSelect(1 downto 0),
            Y   =>  curDigit(3 downto 0)   
        );
    
    
    
    Counter1: Counter_2bit
        port map (
            EN      =>  PULSE,       
            CLK     =>  CLK,
            CLR     =>  '0',
            ROLL    =>  open,
            Q       =>  digitSelect(1 downto 0)
        );
    
    
    Hex1: HexTo7SegDecoder
        port map (
            HEX     =>  curDigit(3 downto 0),    
            SEGMENT =>  SEGMENT(0 to 6)                
        );
    
        
    Decoder1: Decoder2to4_ALO
        port map (
            A   =>  digitSelect(1 downto 0),
            Y   =>  ANODE(3 downto 0)
        );
        
  
    



end architecture;
