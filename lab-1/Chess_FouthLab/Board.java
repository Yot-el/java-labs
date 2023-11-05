import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
    private Figure  fields[][] = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);

    public char getColorGaming() {
        return colorGaming;
    }

    public void setColorGaming(char colorGaming) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'),new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'),new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
        };
    }

    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure == null){
            return "    ";
        }
        return " "+figure.getColor()+figure.getName()+" ";
    }

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    public void move_figure(int row1, int col1, int row2, int col2 ) throws Error{
        Figure figure;

        try {
            figure =  this.fields[row1][col1];
        }
        catch (Exception e) {
            throw new Error("На выбранном вами поле нет фигуры");
        }

        if (figure.getColor() != getColorGaming()) {
            throw new Error("На этом поле находится фигура не вашего цвета");
        }

        /* ---------- */
        // Поведение пешки отдельно, т.к. она ходит и бьет по разным траекториям
        if (figure.getClass().getSimpleName().equals("Pawn")) {
            Figure nextField;

            try {
                nextField = this.fields[row2][col2];
            } catch (IndexOutOfBoundsException e) {
                throw new Error("Вы выбрали клетку за пределами игрового поля");
            }

            if (nextField != null && figure.canAttack(row1, col1, row2, col2, nextField)) {
                this.attack_figure(row1, col1, row2, col2, figure);
                return;
            }
            else if (nextField != null) {
                throw new Error("Поле уже занято фигурой вашего цвета");
            }
            else if (figure.canMove(row1, col1, row2, col2, fields)) {
                System.out.println("move");
                this.fields[row2][col2] = figure;
                this.fields[row1][col1] = null;
                return;
            }

            throw new Error("Эта фигура не может пройти на такую клетку");
        }

        // Если фигура может пройти до [row2][col2] (нет препятствий на пути и траектория соотв. ходу фигуры)
        if (figure.canMove(row1, col1, row2, col2, this.fields)) {
            Figure nextField = this.fields[row2][col2];

            // Проверка на шах для короля (не может подставляться под удар сам)
            if (figure.getClass().getSimpleName().equals("King") && this.is_check(row2, col2, this.getColorGaming())) {
                throw new Error("Король не может подставляться под удар");
            }

            if (nextField != null) {
                if (figure.canAttack(row1, col1, row2, col2, nextField)) {
                    this.attack_figure(row1, col1, row2, col2, figure);

                    // Проверка на шах
                    char oppositeKingColor = this.getColorGaming() == 'w' ? 'b' : 'w';
                    System.out.println(oppositeKingColor);
                    int[] oppositeKingPos = this.find_king_position(oppositeKingColor);

                    if (this.is_check(oppositeKingPos[0], oppositeKingPos[1], oppositeKingColor)) {
                        System.out.println("Шах вражескому королю!");
                        
                        if (this.is_mate(row2, col2, oppositeKingPos[0], oppositeKingPos[1], oppositeKingColor)) {
                            throw new GameOverError("Вражескому королю поставлен мат! Игра закончена");
                        }
                    }
                    return;
                }

                throw new Error("Поле уже занято фигурой вашего цвета");
            }

            // Если конечное поле - пустое
            System.out.println("move");
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;
            
            // Проверка на шах
            char oppositeKingColor = this.getColorGaming() == 'w' ? 'b' : 'w';
            System.out.println(oppositeKingColor);
            int[] oppositeKingPos = this.find_king_position(oppositeKingColor);
            
            if (this.is_check(oppositeKingPos[0], oppositeKingPos[1], oppositeKingColor)) {
                System.out.println("Шах вражескому королю!");

                if (this.is_mate(row2, col2, oppositeKingPos[0], oppositeKingPos[1], oppositeKingColor)) {
                    throw new GameOverError("Вражескому королю поставлен мат! Игра закончена");
                }
            }

            return;
        }

        throw new Error("Эта фигура не может пройти на такую клетку");
    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for(int row = 7; row > -1; row--){
            System.out.print(row);
            for(int col = 0; col< 8; col++){
                System.out.print("|"+getCell(row, col));
             }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col = 0; col < 8; col++){
            System.out.print("    "+col);
        }
    }

    public void attack_figure(int row1, int col1, int row2, int col2, Figure figure) {
        System.out.println("attack");
        switch (this.fields[row2][col2].getColor()){
            case 'w': this.takeWhite.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
            case 'b': this.takeBlack.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
        }
        this.fields[row2][col2] = figure;
        this.fields[row1][col1] = null;
    }

    // Проверка шаха
    public boolean is_check(int kingRow, int kingCol, char kingColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];

                if (figure == null || figure.getColor() == kingColor) {
                    continue;
                }

                if (figure.getClass().getSimpleName().equals("Pawn") && figure.canAttack(i, j, kingRow, kingCol, this.fields[kingRow][kingCol])) {
                    return true;
                }

                if (figure.canMove(i, j, kingRow, kingCol, fields) && figure.canAttack(i, j, kingRow, kingCol, this.fields[kingRow][kingCol])) {
                    return true;
                }
            }
        }
        return false;
    }

    // Проверка мата
    public boolean is_mate(int row, int col, int kingRow, int kingCol, char kingColor) {
        // 1. проверка на то, может ли король сбежать(есть ли хоть одна позиция короля не под шахом)
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (kingRow + i > 8 || kingRow + i < 0 || kingCol + j > 8 || kingCol + j < 0) continue;
                if (i == 0 && j == 0) continue; // Уже проверяли ранее данную клетку

                if (this.fields[kingRow + i][kingCol + j] == null && !(this.is_check(kingRow + i, kingCol + j, kingColor))) {
                    System.out.printf("Координаты точки короля: %d %d", kingRow + i, kingCol + j);
                    return false;
                }
            }
        }
        // 2. проверка на то, можно ли защитить короля от опасной фигуры
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];

                if (figure == null || figure.getColor() != kingColor) {
                    continue;
                }

                if (figure.getClass().getSimpleName().equals("Pawn") && figure.canAttack(i, j, row, col, this.fields[row][col])) {
                    System.out.printf("Координаты точки крутой пешки: %d %d", i, j);
                    return false;
                }

                if (figure.canMove(i, j, row, col, this.fields) && figure.canAttack(i, j, row, col, this.fields[row][col])) {
                    System.out.printf("Координаты точки крутой фигуры: %d %d", i, j);
                    return false;
                }
            }
        }

        return true;
    }

    public int[] find_king_position(char color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];
                if (figure != null && figure.getClass().getSimpleName().equals("King") && figure.getColor() == color) {
                    int[] kingPosition = {i, j};
                    return kingPosition;
                }
            }
        }

        return null;
    }
}
