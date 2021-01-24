/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoedb;

import helpers.DatabaseHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TicTacToeDB {

    Connection con;
    PreparedStatement pst;
    int playerExistFlag = 0;
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
    public TicTacToeDB(int i[], int j[], String sentNames[], String winner) {
        player1 = sentNames[0];
        player2 = sentNames[1];
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
        if (player2.toLowerCase().equals("computer")) {
            addNewGame(player1, winner);
        } else {
            addNewGame(player1, player2, winner);
        }

        //****Add game steps****//
        addGameSteps(row, col, playerNames);
    }
    
    //------------------------------------------------------------------------//
    //****Retreive match IDs(TEST)****//\\****Fixed to retreive all games whether player1 or player2****\\
    public int[] getPlayerMatchIDs(String player_name){
        openCon();
        ArrayList<Integer> tempGamesID = new ArrayList<>();
        int[] gamesIDarr = new int[0];
        int i=0;
        ResultSet rs = null;
        int playerID = getPlayerID(player_name);
        try {
            PreparedStatement getGamesID
                    = con.prepareStatement("SELECT game_id,player1_id ,player2_id "
                            + "FROM game WHERE player1_id = ? OR player2_id = ?;");
            getGamesID.setInt(1,playerID);
            getGamesID.setInt(2,playerID);  
            rs = getGamesID.executeQuery();
            boolean flag1 = rs.next();
            int flag2 = 0;
            if(flag1){
                flag2 = 1;
                if(rs.getInt(2) == playerID){
                    while(flag1){
                        tempGamesID.add(rs.getInt(1));
                        flag1 = rs.next();
                    }
                } else if(rs.getInt(3) == playerID){ 
                    while(flag1){
                        tempGamesID.add(rs.getInt(1));
                        flag1 = rs.next();
                    }
                }
                gamesIDarr = new int[tempGamesID.size()];
                while(i <tempGamesID.size()){
                    gamesIDarr[i] =  tempGamesID.get(i);
                    i++;
                }
            }
            if(flag1 != true && flag2 == 0){
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
            closeCon();
        }
        return gamesIDarr;
    }

    
    //------------------------------------------------------------------------//
    //****Retreive opponents IDs****//\\****Fixed to retreive all games whether player1 or player2****\\
    public ArrayList getPlayerOpponentsID(String playerName){
        ArrayList<Integer> opponentList = new ArrayList<>();
        ResultSet rs = null;
        int count=0;
        try {
            int playerID = getPlayerID(playerName);
            PreparedStatement getOpponentsID
                    = con.prepareStatement("SELECT game_id,player1_id,player2_id "
                            + "FROM tictactoe.game WHERE player1_id = ? OR player2_id = ?;");
            getOpponentsID.setInt(1, playerID);
            getOpponentsID.setInt(2, playerID);
            rs = getOpponentsID.executeQuery();
            boolean flag1 = rs.next();
            int flag2 = 0;
            if(flag1){
                flag2 = 1;
                while(flag1){
                    count++;
                    if(rs.getInt(2) == playerID){ 
                        opponentList.add(rs.getInt(3));
                        flag1 = rs.next();
                    } else{ 
                        opponentList.add(rs.getInt(2));
                        flag1 = rs.next();
                    }
                }
            } 
            if(flag1 != true && flag2 == 0){    //to check if name doesn't exist
                opponentList.add(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
        }
        return opponentList;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive Player opponents Name****//
    public String[] getPlayerOpponentsName(String playerName){
        openCon();
        ArrayList<Integer> opponentList = new ArrayList<>();
        opponentList = getPlayerOpponentsID(playerName);
        ArrayList<String> opponentNameList = new ArrayList<>();
        String[] opponentArr = new String[0];
        int i = 0;
        int size = 0;
        ResultSet rs = null;
        try {
            if(opponentList.get(0) != -1){
                int playerID = getPlayerID(playerName);
                System.out.println(playerID);
                while(size < opponentList.size()){
                    PreparedStatement getOpponentsName
                            = con.prepareStatement("SELECT name FROM player WHERE id = ?;");
                    getOpponentsName.setInt(1, opponentList.get(size));
                    rs = getOpponentsName.executeQuery();
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
            } else {
                return null;   
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
            closeCon();
        }
        return opponentArr;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive Player Dates****//\\****Fixed to retreive all games whether player1 or player2****\\
    public String[] getPlayerDates(String playerName){
        openCon();
         ArrayList<String> DatesList = new ArrayList<>();
        String[] DatesArr = new String[0];
        int i = 0;
        ResultSet rs = null;
        try {
            int playerID = getPlayerID(playerName);
            PreparedStatement getDates
                    = con.prepareStatement("SELECT game_date FROM game WHERE player1_id = ? OR player2_id = ? ");
            getDates.setInt(1, playerID);
            getDates.setInt(2, playerID);
            rs = getDates.executeQuery();
            boolean flag1 = rs.next();
            int flag2 = 0;
            if(flag1){
                flag2 = 1;
                while(flag1){
                    DatesList.add(rs.getString(1));
                    flag1 = rs.next();
                }
                DatesArr = new String[DatesList.size()];
                while(i < DatesList.size()){
                    DatesArr[i] = DatesList.get(i);
                    i++;
                }
            }
            if(flag1 == false && flag2 == 0){   //to check if name doesn't exist 
                return null;    //for best practice
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
            closeCon();
        }
        return DatesArr;
    }
    
    //------------------------------------------------------------------------//
    //****Retreive Player Results****//
    public String[] getPlayerResults(String playerName){
        openCon();
        ArrayList<String> ResultsList = new ArrayList<>();
        String[] ResultsArr = new String[0];
        int i=0;
        ResultSet rs = null;
        try {
            int player1ID = getPlayerID(playerName);
            PreparedStatement getDates
                    = con.prepareStatement("SELECT result FROM game WHERE player1_id = ? ");
            getDates.setInt(1, player1ID);
            rs = getDates.executeQuery();
            boolean flag1 = rs.next();
            int flag2 = 0;
            if(flag1){
                flag2 = 1;
                while(flag1){
                    ResultsList.add(rs.getString(1));
                    flag1 = rs.next();
                }
                ResultsArr = new String[ResultsList.size()];
                while(i < ResultsList.size()){
                    ResultsArr[i] = ResultsList.get(i);
                    i++;
                }
            }
            if(flag1 != true && flag2 == 0){
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
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
        ResultSet rs = null;
        try {
            PreparedStatement getRow
                    = con.prepareStatement("SELECT place_i FROM steps WHERE game_id = ?;");
            getRow.setInt(1, game_id);
            rs = getRow.executeQuery();
            while(rs.next()){
                RowMoves.add(rs.getInt(1));
            }
            RowArr = new int[RowMoves.size()];
            while(i < RowMoves.size()){
                RowArr[i] = RowMoves.get(i);
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
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
        ResultSet rs = null;
        try {
            PreparedStatement getcolumn
                    = con.prepareStatement("SELECT place_j FROM steps WHERE game_id = ?;");
            getcolumn.setInt(1, game_id);
            rs = getcolumn.executeQuery();
            while(rs.next()){
                ColumnMoves.add(rs.getInt(1));
            }
            ColumnArr = new int[ColumnMoves.size()];
            while(i < ColumnMoves.size()){
                ColumnArr[i] = ColumnMoves.get(i);
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
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
        ResultSet rs = null;
        try {
            PreparedStatement getStepPlayerName
                    = con.prepareStatement("SELECT name " + "FROM tictactoe.steps,player " + 
                            "WHERE player_id = id && game_id = ?;");
            getStepPlayerName.setInt(1, game_id);
            rs = getStepPlayerName.executeQuery();
            while(rs.next()){
                StepPlayerNameMoves.add(rs.getString(1));
            }
            StepPlayerNameArr = new String[StepPlayerNameMoves.size()];
            while(i < StepPlayerNameMoves.size()){
                StepPlayerNameArr[i] = StepPlayerNameMoves.get(i);
                i++;
            }
            for(String x : StepPlayerNameArr){
                System.out.println(x);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
            closeCon();
        }
        return StepPlayerNameArr;
    }
    
    //------------------------------------------------------------------------//
    //****Add steps****// 
    public void addGameSteps(int i[], int j[], String playerName[]) {
        openCon();
        ResultSet rs = null;
        try {
            for (int x = 0; x < i.length; x++) {
                int playerID = getPlayerID(playerName[x]);
                PreparedStatement getLastGameID
                        = con.prepareStatement("SELECT game_id FROM tictactoe.game ORDER BY game_id DESC LIMIT 1;");
                rs = getLastGameID.executeQuery();
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
        } finally{
            try {
                rs.close();
            } catch (SQLException RSex) {
                RSex.printStackTrace();
            }
            closeCon();
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
        ResultSet rs = null;
        try {
            pst = con.prepareStatement("select id from player where name = ? ");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next() == true) {
                playerExistFlag = 1;
            } else {
                playerExistFlag = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
                try {
                    rs.close();
                } catch (SQLException RSex) {
                    RSex.printStackTrace();
                }
        }
        return playerExistFlag;
    }

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
        } finally{
            closeCon();
        }
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
        } finally{
            closeCon();
        } 
    }

    //------------------------------------------------------------------------//
    //****Get Player Id****// 
    public int getPlayerID(String playerName) {
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.prepareStatement("select id from player where name = ?");
            pst.setString(1, playerName);
            rs = pst.executeQuery();
            if(rs.next() == true){
                return rs.getInt(1);
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return 0;
    }
    
        public int isExistingPlayer(String name) {
        ResultSet rs = null;
        openCon();
        try {
            pst = con.prepareStatement("select id from player where name = ? ");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next() == true) {
                playerExistFlag = 1;
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            if(playerExistFlag == 1) {
                try {
                    rs.close();
                } catch (SQLException RSex) {
                    RSex.printStackTrace();
                }
            }
            closeCon();
        }
        return playerExistFlag;
    }
        
    //------------------------------------------------------------------------//
    //****Open Connection****//
    public void openCon() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(DatabaseHelper.dbURL, DatabaseHelper.dbUsername, DatabaseHelper.dbPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //------------------------------------------------------------------------//
    //****Close Connection****//
    public void closeCon() {
        try {
//            pst.close();
            if (pst != null) {
                pst.close();
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
