package com.example.layrinth_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Labyrinth extends View {



    private enum Direction{
        UP, DOWN, LEFT, RIGHT
    }
    public static int score = 0;

    private Cell[][] cells;
    private Cell player, exit;
    private static final int COLS = 7, ROWS = 10;
    private static final float Wall_THICKNESS = 4;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint, playerPaint, exitPaint, textPaint;
    private Random random;

    public Labyrinth(Context context) {
        super(context);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(Wall_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);

        exitPaint = new Paint();
        exitPaint.setColor(Color.BLUE);


        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        random = new Random();
        createLabyrinth();
    }



    private Cell getNeighbour(Cell cell){
        ArrayList<Cell> neighbours = new ArrayList<>(); //Potřeba dynamicky měnit velikost po tom co bude ArrayList inicalizován
        //left neighbours
        if(cell.col > 0) //Kontrola, aby jsme se nedostali na špatný index
            if(!cells[cell.col-1][cell.row].visited)//Pokud soused nebyl navštíven
                neighbours.add(cells[cell.col-1][cell.row]);//Přidáme ho do sousedů
        //right neighbours
        if(cell.col < COLS - 1)
            if(!cells[cell.col+1][cell.row].visited)
                neighbours.add(cells[cell.col+1][cell.row]);
        //top neighbours
        if(cell.row > 0)
            if(!cells[cell.col][cell.row-1].visited)
                neighbours.add(cells[cell.col][cell.row-1]);
        //bottom neighbours
        if(cell.row < ROWS - 1)
            if(!cells[cell.col][cell.row+1].visited)
                neighbours.add(cells[cell.col][cell.row+1]);
        //Vybereme náhodný index
        if(neighbours.size() > 0) { //Kontrola zda máme nějakého souseda
            int index = random.nextInt(neighbours.size());//Pokud máme 3 nenavšťívené sousedy bude náš index 0 nebo 1 nebo 2
            return neighbours.get(index);
        }
        return null;
    }

    private void removeWall(Cell current, Cell next) {
        //Pokud je next nad current
        if(current.col == next.col && current.row == next.row+1){
            current.topWall = false;
            next.bottomWall = false;
        }
        //Pokud je next pod current
        if(current.col == next.col && current.row == next.row-1){
            current.bottomWall = false;
            next.topWall = false;
        }
        //Pokud je next vlevo od current
        if(current.col == next.col+1 && current.row == next.row){
            current.leftWall = false;
            next.rightWall = false;
        }
        //Pokud je next vpravo od current
        if(current.col == next.col-1 && current.row == next.row){
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    //Vytvoření labyrinthu
    private void createLabyrinth(){
        //Vytvoření Stacku pro rekurzivní backtrack
        Stack<Cell> stack = new Stack<>();
        Cell current, next;
        //Vytvoření mřížky
        cells = new Cell[COLS][ROWS];

        for(int x = 0; x<COLS; x++){
            for(int y = 0; y<ROWS; y++){
                cells[x][y] = new Cell(x, y);
            }
        }

        //Pozice hráče a cíle
        player = cells[0][0];
        exit = cells[COLS-1][ROWS-1];
        //Definice proměnných
        current = cells[0][0];
        current.visited = true;

        //Rekurzivní bactrack
        do{
            next = getNeighbour(current); //Díváme se zda máme souseda
            if(next != null){ //Pokud najdeme souseda provedeme následující kód
                removeWall(current,next); //Zničíme stěnu
                stack.push(current); //Uložíme current position
                current = next; //Z alší pozice uděláme aktuální
                current.visited = true; //Dáme že jsem ji navštívili
            }
            else //Pokud souseda nenajdeme vrátíme se zpátky
                current = stack.pop();
        }while(!stack.empty()); //Provádáme tak dlouho dokud není stack prázdný (Prošli jsme celý labyrinth)


    }
    //Vykreslení
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Získáme počet pixelů
        int width = getWidth();
        int height = getHeight();
        //Vypočítáme velikost buněk
        if(width/height < COLS/ROWS)
            cellSize = width/(COLS + 1);
        else
            cellSize = height/(ROWS + 1);

        hMargin = (width - COLS*cellSize)/2;
        vMargin = (height - ROWS*cellSize)/2;
        //Aby jsme nemuseli furt přidávat margin
        canvas.translate(hMargin, vMargin);
        //Vykreslení mřížky
        for(int x = 0; x<COLS; x++){
            for(int y = 0; y<ROWS; y++){
                if(cells[x][y].topWall)
                    canvas.drawLine(x*cellSize,y*cellSize, (x+1)*cellSize, (y*cellSize), wallPaint );
                if(cells[x][y].leftWall)
                    canvas.drawLine(x*cellSize,y*cellSize, x*cellSize, (y+1)*cellSize, wallPaint );
                if(cells[x][y].bottomWall)
                    canvas.drawLine(x*cellSize,(y+1)*cellSize, (x+1)*cellSize, (y+1)*cellSize, wallPaint );
                if(cells[x][y].rightWall)
                    canvas.drawLine((x+1)*cellSize,y*cellSize, (x+1)*cellSize, (y+1)*cellSize, wallPaint );
            }
        }

        float margin = cellSize/10;
        //Vykresení hráče, cíle, score a highScore
        canvas.drawRect(player.col*cellSize+margin, player.row*cellSize+margin, (player.col+1)*cellSize-margin, (player.row+1)*cellSize-margin, playerPaint);
        canvas.drawRect(exit.col*cellSize+margin, exit.row*cellSize+margin, (exit.col+1)*cellSize-margin, (exit.row+1)*cellSize-margin, exitPaint);
        canvas.drawText("Score: " + score, 1100,-20,textPaint);
        canvas.drawText("Highscore: " + Labyrinth_creator.highsScore, 400,-20,textPaint);

    }

    //Pohyb hráče
    private void movePlayer(Direction direction){
        switch(direction){
            case UP:
                if(!player.topWall)
                    player = cells[player.col][player.row-1];
                break;
            case DOWN:
                if(!player.bottomWall)
                    player = cells[player.col][player.row+1];
                break;
            case LEFT:
                if(!player.leftWall)
                    player = cells[player.col-1][player.row];
                break;
            case RIGHT:
                if(!player.rightWall)
                    player = cells[player.col+1][player.row];
        }
        checkExit();
        invalidate();

    }
    //Kontrola zda hráč dosáhl konce
    private void checkExit(){
        if(player == exit){
            createLabyrinth();
            score++;
            Labyrinth_creator.highsScore++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            return true;
        if(event.getAction() == MotionEvent.ACTION_MOVE){//Zachytává pohyb nasšeho prstu po display
            //Kde se nachází prst
            float x = event.getX();
            float y = event.getY();
            //Kde se nachází hráč
            float playerCenterX = hMargin + (player.col+0.5f)*cellSize;//0.5f -> f-float
            float playerCenterY = vMargin + (player.row+0.5f)*cellSize;
            //Výpočet rozdílu poloh (může být  +  -)
            float dx = x - playerCenterX;
            float dy = y - playerCenterY;
            //Výpočet absolutních hodnot
            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if(absDx > cellSize || absDy > cellSize){ //Pohnu s hráčem pokud je velikost změny polohy větší než velikost buňky
                if(absDx > absDy){//Pohyb po X-ose
                    if(dx > 0){
                        movePlayer(Direction.RIGHT);
                    }
                    else{
                        movePlayer(Direction.LEFT);
                    }
                }
                else{//Pohyb po Y-ose
                    if(dy > 0){
                        movePlayer(Direction.DOWN);
                    }
                    else{
                        movePlayer(Direction.UP);
                    }
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }


    //Třída na vytvoření jedné buňky
    private class Cell {
        boolean
                topWall = true,
                leftWall = true,
                bottomWall = true,
                rightWall = true,
                visited = false;

        int col, row;
        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}

