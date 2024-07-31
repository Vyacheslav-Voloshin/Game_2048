package game;

import java.awt.*;

/*
У грі 2048 року поле складається з 16 плиток,
кожна з яких має певну вагу.
Крім ваги у плитки ще буде власний колір та
колір тексту, яким буде відображатися вага плитки.
Кольори плиток знаходяться в діапазоні від світло-сірого.
до червоного, а колір тексту залежатиме від кольору плитки.
Клас Tile, який описує одну плитку.
 */
public class Tile {
     int value;

    public Tile(int value) {
        this.value = value;
    }

    public Tile() {
        this.value = 0;
    }

    public boolean isEmpty(){
       return value==0?true:false;
    }

    //Метод getFontColor, який повертає новий колір
    //(об'єкт типу Color) (0x776e65) у разі,
    //якщо вага плитки менше 16, інакше – 0xf9f6f2.
    public Color getFontColor(){
        return value>=16?new Color(0xf9f6f2):new Color(0x776e65);
    }


    //Метод getTileColor, що повертає колір плитки в
    //залежно від її ваги відповідно до
    //нижченаведеними значеннями:
    public Color getTileColor(){
            switch (value){
                case 0: return new Color(0xcdc1b4);
                case 2: return new Color(0xeee4da);
                case 4: return new Color(0xede0c8);
                case 8: return new Color(0xf2b179);
                case 16: return new Color(0xf59563);
                case 32: return new Color(0xf67c5f);
                case 64: return new Color(0xf65e3b);
                case 128: return new Color(0xedcf72);
                case 256: return new Color(0xedcc61);
                case 512: return new Color(0xedc850);
                case 1024: return new Color(0xedc53f);
                case 2048: return new Color(0xedc22e);

                default:return new Color(0xff0000);

            }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
