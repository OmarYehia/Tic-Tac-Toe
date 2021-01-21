/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoedb;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToeDB {

    Connection con;
    PreparedStatement pst;
    static int playerExistFlag = 0;
    String player1, player2, winner;
    String playerNames[];
    int row[], col[], total[];
    public int testtt = 0;
    //------------------------------------------------------------------------//
    //****Default constructor****//
    public TicTacToeDB(){
    
    }

    //------------------------------------------------------------------------//
    //****Fake constructor****//
    public TicTacToeDB(String player1,String player2,String result) {
                //****Add players****//
        addNewPlayer(player1);
        addNewPlayer(player2);
        addNewGame(player1, player2, result);
    }

//TicToeDB(int i[0,1],int j[0,1],String steps["ali","computer"],String winner)
    //------------------------------------------------------------------------//
    //****Parametrized constructor****//
    public TicTacToeDB(int i[], int j[], String[] sentNames, String winner) {
        System.out.println("****Entered parm constructor****");
        player1 = sentNames[0];
        player2 = sentNames[1];
        System.out.println("****Old array****");
        for (int y = 0; y < i.length; y++) {
            System.out.println(i[y]);
            System.out.println(j[y]);
            System.out.println(sentNames[y]);
        }
        System.out.println("****winner: " + winner);
        System.out.println("****Adjusting array****");
        int y = 0;
        while (y < 9 && sentNames[y] != null) {
            row = new int[y + 1];
            col = new int[y + 1];
            playerNames = new String[y + 1];
            for (int w = 0; w <= y; w++) {
                row[w] = i[w];
                col[w] = j[w];
                playerNames[w] = sentNames[w];
            }
            y++;
        }

        //****Add players****//
        addNewPlayer(player1);
        addNewPlayer(player2);

        //****Add new game****//        
        if (player2.toLowerCase() == "computer") {
            addNewGame(player1, winner);
        } else {
            addNewGame(player1, player2, winner);
        }

        //****Add game steps****//
        addGameSteps(row, col, playerNames);
    }

    //------------------------------------------------------------------------//
    //****Retreive match IDs****// 
    public int[] getPlayerMatchIDs(String player_name){
        openCon();
        ArrayList<Integer> tempGamesID = new ArrayList<>();
        int[] gamesIDarr = new int[0];
        int i=0;
        try {
            int playerID = getPlayerID(player_name);
            PreparedStatement getGamesID
                    = con.prepareStatement("SELECT game_id FROM game WHERE player1_id = ?;");
            getGamesID.setInt(1,playerID);
            ResultSet rs = getGamesID.executeQuery();
            while(rs.next()){
                tempGamesID.add(rs.getInt(1));
            }
            gamesIDarr = new int[tempGamesID.size()];
            while(i <tempGamesID.size()){
                gamesIDarr[i] =  tempGamesID.get(i);
                i++;
            }
//            for(int x : gamesIDarr){
//                System.out.println(x);
//            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return gamesIDarr;
    }

    //------------------------------------------------------------------------//
    //****Retreive opponents****//
//    public String[] getPlayerOpponents(String player1Name){
//        openCon();
//         ArrayList<String> opponentList = new ArrayList<>();
//        String[] opponentArr = new String[0];
//        int i=0;
//        try {
//            int player1ID = getPlayerID(player1Name);
//            PreparedStatement getOpponents
//                    = con.prepareStatement("SELECT name FROM player WHERE id IN "
//                            + "(SELECT player2_id FROM tictactoe.game WHERE player1_id = ?);");
//            getOpponents.setInt(1, player1ID);
//            ResultSet rs = getOpponents.executeQuery();
//            while(rs.next()){
//                opponentList.add(rs.getString(1));
//            }
//            opponentArr = new String[opponentList.size()];
//            while(i < opponentList.size()){
//                opponentArr[i] = opponentList.get(i);
//                i++;
//            }
////            for(String s : opponentArr){
////                System.out.println(s);
////            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally{
//            closeCon();
//        }
//        return opponentArr;
//    }
    
    //------------------------------------------------------------------------//
    //****Retreive opponents IDs****//
    public ArrayList getPlayerOpponentsID(String player1Name){
        openCon();
         ArrayList<Integer> opponentList = new ArrayList<>();
        int[] opponentArr = new int[0];
        int i=0;
        try {
            int player1ID = getPlayerID(player1Name);
            PreparedStatement getOpponentsID
                    = con.prepareStatement("SELECT player2_id FROM tictactoe.game WHERE player1_id = ?;");
            getOpponentsID.setInt(1, player1ID);
            ResultSet rs = getOpponentsID.executeQuery();
            while(rs.next()){
                opponentList.add(rs.getInt(1));
            }
            opponentArr = new int[opponentList.size()];
            while(i < opponentList.size()){
                opponentArr[i] = opponentList.get(i);
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return opponentList;
    }
    
    
    //------------------------------------------------------------------------//
    //****Retreive Player opponents Name****//
    public String[] getPlayerOpponentsName(String player1Name){
        openCon();
        ArrayList<Integer> opponentList = new ArrayList<>();
        opponentList = getPlayerOpponentsID(player1Name);
        ArrayList<String> opponentNameList = new ArrayList<>();
        String[] opponentArr = new String[0];
        int i=0;
        int size=0;
        try {
            int player1ID = getPlayerID(player1Name);
            while(size < opponentList.size()){
                PreparedStatement getOpponentsName
                        = con.prepareStatement("SELECT name FROM player WHERE id = ?;");
                getOpponentsName.setInt(1, opponentList.get(size));
                ResultSet rs = getOpponentsName.executeQuery();
                while(rs.next()){
                    opponentNameList.add(rs.getString(1));
                }
                size++;
            }
            opponentArr = new String[opponentList.size()];
            while(i < opponentNameList.size()){
                opponentArr[i] = opponentNameList.get(i);
                i++;
            }
            for(String s : opponentArr){
                System.out.println(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return opponentArr;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive Player Dates****//
    public String[] getPlayerDates(String player1Name){
        openCon();
         ArrayList<String> DatesList = new ArrayList<>();
        String[] DatesArr = new String[0];
        int i=0;
        try {
            int player1ID = getPlayerID(player1Name);
            PreparedStatement getDates
                    = con.prepareStatement("SELECT game_date FROM game WHERE player1_id = ? ");
            getDates.setInt(1, player1ID);
            ResultSet rs = getDates.executeQuery();
            while(rs.next()){
                DatesList.add(rs.getString(1));
            }
            DatesArr = new String[DatesList.size()];
            while(i < DatesList.size()){
                DatesArr[i] = DatesList.get(i);
                i++;
            }
//            for(String s : DatesArr){
//                System.out.println(s);
//            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return DatesArr;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive Player Results****//
    public String[] getPlayerResults(String player1Name){
        openCon();
         ArrayList<String> ResultsList = new ArrayList<>();
        String[] ResultsArr = new String[0];
        int i=0;
        try {
            int player1ID = getPlayerID(player1Name);
            PreparedStatement getDates
                    = con.prepareStatement("SELECT result FROM game WHERE player1_id = ? ");
            getDates.setInt(1, player1ID);
            ResultSet rs = getDates.executeQuery();
            while(rs.next()){
                ResultsList.add(rs.getString(1));
            }
            ResultsArr = new String[ResultsList.size()];
            while(i < ResultsList.size()){
                ResultsArr[i] = ResultsList.get(i);
                i++;
            }
            for(String s : ResultsArr){
                System.out.println(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return ResultsArr;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive row steps****// 
    public void getRowSteps(int mathchID){
        openCon();
        try {
            PreparedStatement getSteps
                    = con.prepareStatement("SELECT game_id FROM game WHERE player1_id = ?;");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
    }
    
    
    
    //------------------------------------------------------------------------//
    //****Get Match Row****//
    public int[] getMatchRow(int game_id){
        openCon();
        ArrayList<Integer> RowMoves = new ArrayList<>();
        int RowArr[] = new int[0];
        int i = 0;
        try {
            PreparedStatement getRow
                    = con.prepareStatement("SELECT place_i FROM steps WHERE game_id = ?;");
            getRow.setInt(1, game_id);
            ResultSet rs = getRow.executeQuery();
            while(rs.next()){
                RowMoves.add(rs.getInt(1));
            }
            RowArr = new int[RowMoves.size()];
            while(i < RowMoves.size()){
                RowArr[i] = RowMoves.get(i);
                i++;
            }
            for(int x : RowArr){
                System.out.println(x);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return RowArr;
    }
    
    //------------------------------------------------------------------------//
    //****Get Match Columns****//
    public int[] getMatchColumn(int game_id){
        openCon();
        ArrayList<Integer> ColumnMoves = new ArrayList<>();
        int ColumnArr[] = new int[0];
        int i = 0;
        try {
            PreparedStatement getcolumn
                    = con.prepareStatement("SELECT place_j FROM steps WHERE game_id = ?;");
            getcolumn.setInt(1, game_id);
            ResultSet rs = getcolumn.executeQuery();
            while(rs.next()){
                ColumnMoves.add(rs.getInt(1));
            }
            ColumnArr = new int[ColumnMoves.size()];
            while(i < ColumnMoves.size()){
                ColumnArr[i] = ColumnMoves.get(i);
                i++;
            }
            for(int x : ColumnArr){
                System.out.println(x);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return ColumnArr;
    }
    
    //****Get Match Step Player Name****//
    public String[] getMatchStepPlayerName(int game_id){
        openCon();
        ArrayList<String> StepPlayerNameMoves = new ArrayList<>();
        String StepPlayerNameArr[] = new String[0];
        int i = 0;
        try {
            PreparedStatement getStepPlayerName
                    = con.prepareStatement("SELECT name " + "FROM tictactoe.steps,player " + 
                            "WHERE player_id = id && game_id = ?;");
            getStepPlayerName.setInt(1, game_id);
            ResultSet rs = getStepPlayerName.executeQuery();
            while(rs.next()){
                StepPlayerNameMoves.add(rs.getString(1));
            }
            StepPlayerNameArr = new String[StepPlayerNameMoves.size()];
            while(i < StepPlayerNameMoves.size()){
                StepPlayerNameArr[i] = StepPlayerNameMoves.get(i);
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            closeCon();
        }
        return StepPlayerNameArr;
    }
    
    //------------------------------------------------------------------------//
    //****Main****//
    public static void main(String[] args) {
        TicTacToeDB t= new TicTacToeDB();
//        int test[] = t.getMatchIDs("sad");
//        for(int n : test){
//            System.out.println(n);
//        }
//        TicTacToeDB T = new TicTacToeDB("a","b","a");
//        t.getPlayerOpponentsID("karim");
//        t.getPlayerDates("karim");
//        t.getPlayerResults("karim");
//        t.getPlayerMatchIDs("karim");
//        t.getPlayerOpponentsName("karim");
//        t.getMatchRow(105);
//        t.getMatchColumn(105);
//        t.getMatchStepPlayerName(105);
            
    }
    
    //------------------------------------------------------------------------//
    //****Add steps****// 
    public void addGameSteps(int i[], int j[], String playerName[]) {
        openCon();
        try {
            for (int x = 0; x < i.length; x++) {
                int playerID = getPlayerID(playerName[x]);
                PreparedStatement getLastGameID
                        = con.prepareStatement("SELECT game_id FROM tictactoe.game ORDER BY game_id DESC LIMIT 1;");
                ResultSet rs = getLastGameID.executeQuery();
                rs.next();
                int gameID = rs.getInt(1);
                PreparedStatement pst
                        = con.prepareStatement("insert into steps (game_id,place_i,player_id,place_j) values (?,?,?,?)");
                pst.setInt(1, gameID);
                pst.setInt(2, i[x]);
                pst.setInt(3, playerID);
                pst.setInt(4, j[x]);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //------------------------------------------------------------------------//
    //****Open Connection****//
    public void openCon() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe", "root", "4994");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //------------------------------------------------------------------------//
    //****Close Connection****//
    public void closeCon() {
        try {
            //pst.close();
            if (pst != null) {
                pst.close();
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //------------------------------------------------------------------------//
    //****Add New Player****//
    public void addNewPlayer(String name) {
        openCon();
        if (checkPlayerExist(name) == 0) {
            try {
                PreparedStatement pst = con.prepareStatement("insert into player (name , score) values (?,0) ");
                pst.setString(1, name);
                pst.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        closeCon();
    }

    //------------------------------------------------------------------------//
    //****Check if Player Exists****//
    public int checkPlayerExist(String name) {
        try {
            pst = con.prepareStatement("select id from player where name = ? ");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next() == true) {
                playerExistFlag = 1;
            } else {
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return playerExistFlag;
    }

    /*
    step(i ,j ,player name)
    /*THREAD
    while(true){
        int i=incoming from server i
        int j=incoming from server j
        int player=incoming from server,1=player1 ,2=player2
        int result =incoming from server,0=continue to play,1=player1 won ,2=player2 won,3=draw
        if(resullt){
            save game
            then break from thread;
        }
    }   
     */
    //------------------------------------------------------------------------//
    //****Add New Game If Multi Player****//
    public void addNewGame(String player1_Name, String player2_Name, String result) {

        openCon();
        try {
            int player1_id, player2_id;
            player1_id = getPlayerID(player1_Name);
            player2_id = getPlayerID(player2_Name);

            // Getting Current Date
            Date d = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(d);

            // Inserting Game Data
            PreparedStatement pst = con.prepareStatement("insert into game "
                    + "(player1_id, player2_id,game_date,result) values (?,?,?,?)");
            pst.setInt(1, player1_id);
            pst.setInt(2, player2_id);
            pst.setString(3, strDate);
            pst.setString(4, result);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeCon();
    }

    //------------------------------------------------------------------------//
    //****Add New Game If Single Player****//
    public void addNewGame(String player1_Name, String result) {
        openCon();
        try {
            int player1_id, result_id;
            player1_id = getPlayerID(player1_Name);
            // Getting Current Date
            Date d = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(d);
            // Inserting Game Data
            PreparedStatement pst = con.prepareStatement("insert into game "
                    + "(player1_id, player2_id,game_date,result) values (?,0,?,?) ");
            pst.setInt(1, player1_id);
            pst.setString(2, strDate);
            pst.setString(3, result);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeCon();
    }

    //------------------------------------------------------------------------//
    //****Get Player Id****// 
    public int getPlayerID(String playerName) {
        openCon();
        try {
            PreparedStatement pst = con.prepareStatement("select id from player where name = ?");
            pst.setString(1, playerName);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    
}
