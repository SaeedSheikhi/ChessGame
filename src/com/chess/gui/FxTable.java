package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Member;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;
import com.sun.javafx.scene.control.skin.CustomColorDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.*;
import java.util.List;

import static com.chess.engine.board.BoardUtils.NUM_TILES;



/**
 * Created by Asus on 2017-07-29.
 */
public class FxTable extends Application{
    public static FxTable INSTANCE;
    public static Stage primaryStage;
    private Scene scene;
    private BorderPane BP;
    private AnchorPane AP;

    private Board chessBoard;
    private BoardPanel boardPanel;

    private BoardDirection boardDirection;
//   private BoardPanel pieceLegalMoves;

    public static final int TILE_SIZE = 80;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    // private final Color lightTileColor = Color.valueOf("#FFFACD");
    private String lightTileColorString = "-fx-background-color: #FFFACD;";
    private String darkTileColorString = "-fx-background-color: #8B4513;";
    private String selectedTileColorDarkString = "-fx-background-color: #99FD80;";
    private String selectedTileColorLightString = "-fx-background-color: #99FD80;";
    private String selectedTileColorString = "-fx-background-color: #33FF33;";

    private final String defaultPieceImagePath = "/art/clean/";

    private final static double H_TILE_GAP = 0;
    private final static double V_TILE_GAP = 0;

    private Tile sourceTile;
    private Tile hoveredTile;
    private Tile destinationTile;
    private TilePanel sourceTilePanel;
    private TilePanel hoveredTilePanel;
    private Piece humanMovedPiece;

    private boolean showLegalMoves;
    private static Member hostMember;
    private static Member guestMember;

    public static void main(String[] args) {
        launch(args);
    }

    public static Member getHostMember() {
        return hostMember;
    }

    public static void setHostMember(Member hostMember) {
        FxTable.hostMember = hostMember;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        INSTANCE = this;
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(event -> JOptionPane.showMessageDialog(null,"Next Time Play More :),Bye"));


        this.scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ChessGame");

        primaryStage.show();
    }

    private Parent createContent() {
        this.chessBoard = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.showLegalMoves = true;

        this.AP = new AnchorPane();
        this.AP.setMinSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);



        this.BP = new BorderPane();
        this.BP.setCenter(this.AP);


        this.boardPanel = new BoardPanel();

        this.BP.setMinSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);

        final int vBoxHeight = 25;
        final int vBoxPadding = 2;
        final int vBoxTotalHeight = vBoxHeight + vBoxPadding * 2;
        final int vBoxSpacing = 4;

        VBox vBox = new VBox();
        vBox.setPrefSize(vBoxHeight, vBoxHeight);
        vBox.setSpacing(vBoxSpacing);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(vBoxPadding))));
        final MenuBar tableMenuBar = createTableMenuBar();
        vBox.getChildren().addAll(tableMenuBar);
        this.BP.setTop(vBox);

        AP.getChildren().addAll( boardPanel, pieceGroup);

        return BP;
    }

    public BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

     class BoardPanel extends TilePane{
        final List<TilePanel> boardTiles;

        BoardPanel() {
            this.boardTiles = new ArrayList<>();
            setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
            pieceGroup.getChildren().clear();

            for (int i = 0; i < NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, boardDirection.traverseTileID(i), Orientation.HORIZONTAL, H_TILE_GAP, V_TILE_GAP);
                this.boardTiles.add(tilePanel);
                tilePanel.setOnMousePressed(e->{
                    tilePanel.assignMoveTileColor();
                });

                tilePanel.setOnMouseReleased(e->{
                    tilePanel.assignTileColor();
                });
                getChildren().add(tilePanel);
            }
        }
         void drawBoard(final Board board) {
            AP.getChildren().clear();
            pieceGroup.getChildren().clear();
            boardPanel.getChildren().clear();
            boardPanel = new BoardPanel();

            AP.getChildren().addAll(boardPanel, pieceGroup);

        }
    }

    private int tileIDFromCoOrdinate(int x, int y) {
        int tileID = x + y * 8;
        return tileID;
    }

    private class TilePanel extends TilePane {
        private final int tileID;
        private String originalColorString;

        TilePanel(final BoardPanel boardPanel, final int tileID, Orientation orientation, double hgap, double vgap) {
            super(orientation, hgap, vgap);
            this.tileID = tileID;
            this.setOnMousePressed(e -> {
                System.out.println("tilePanel Tile ID: "+ this.tileID);
            });
            setPrefSize(TILE_SIZE, TILE_SIZE);
            assignTileColor();

            //setPosition();
            assignTilePieceIcon(chessBoard);
//            highLightLegalMoves(chessBoard);
        }

        public void drawTilePanel (Board board) {

            assignTileColor();
            assignTilePieceIcon(board);

        }

        private void highLightLegalMoves(final Board board){
            if(true){
                for (final Move move : pieceLegalMoves (board)){
                    if (move.getDestinationCoordinate() == this.tileID){
                        assignMoveTileColor();
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board){
            if (humanMovedPiece!= null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()){
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor() {
            if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.THIRD_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileID)) {
                setStyle(tileID % 2 != 0 ? lightTileColorString : darkTileColorString);
                originalColorString = getStyle();
            } else if (BoardUtils.INSTANCE.SECOND_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileID)) {
                setStyle(tileID % 2 == 0 ? lightTileColorString : darkTileColorString);
                originalColorString = getStyle();
            }
        }

        private void assignMoveTileColor() {
            if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.THIRD_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileID)) {
                setStyle(tileID % 2 != 0 ? selectedTileColorLightString : selectedTileColorDarkString);
                originalColorString = getStyle();
            } else if (BoardUtils.INSTANCE.SECOND_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileID) ||
                    BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileID)) {
                setStyle(tileID % 2 == 0 ? selectedTileColorLightString: selectedTileColorDarkString);
                originalColorString = getStyle();
            }
        }

        public void assignTilePieceIcon(final Board board) {
            //TODO figure out why pathing not working 100%
            if (board.getTile(this.tileID).isTileOccupied()) {

                final String path = defaultPieceImagePath + board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1) +
                        board.getTile(this.tileID).getPiece().toString() + ".png";
                final int x = getCoOrdinateX(boardDirection.traverseTileID(this.tileID));
                final int y = getCoOrdinateY(boardDirection.traverseTileID(this.tileID));

                final PieceImage pieceImage = new PieceImage(
                        chessBoard.getTile(tileID).getPiece(),
                        x,
                        y,
                        path);
                pieceGroup.getChildren().add(pieceImage);

                pieceImage.setOnMousePressed(e -> {
                    final int tileCoOrd = boardDirection.traverseTileID(tileIDFromCoOrdinate(x, y));
                    pieceImage.mouseX = e.getSceneX();
                    pieceImage.mouseY = e.getSceneY();

                    if (sourceTile == null) {
                        sourceTilePanel = boardPanel.boardTiles.get(boardDirection.traverseTileID(tileCoOrd));
                        sourceTile = chessBoard.getTile(tileCoOrd);
                        humanMovedPiece = sourceTile.getPiece();

                        if(showLegalMoves) {
                            Collection<Move> selectedPiece = pieceLegalMoves(chessBoard);
                            for (Move move : selectedPiece) {
                                TilePanel tilePanel = boardPanel.boardTiles.get(boardDirection.traverseTileID(move.getDestinationCoordinate()));
                                tilePanel.assignMoveTileColor();

                            }
                        }
                    }
                });

                pieceImage.setOnMouseEntered(e -> {
                    pieceImage.toFront(); // beautiful.
                    if (showLegalMoves) {
                        final int tileCoOrd = boardDirection.traverseTileID(tileIDFromCoOrdinate(x, y));
                        hoveredTilePanel = boardPanel.boardTiles.get(boardDirection.traverseTileID(tileCoOrd));
                        hoveredTile = chessBoard.getTile(tileCoOrd);

                        if (chessBoard.getTile(tileCoOrd).isTileOccupied() &&
                                chessBoard.getTile(tileCoOrd).getPiece().getPieceAlliance() == chessBoard.currentPlayer().getAlliance()) {
                            hoveredTilePanel.setStyle(selectedTileColorString);
                        }
                    }
                });
                pieceImage.setOnMouseExited(e -> {
                    final int tileCoOrd = boardDirection.traverseTileID(tileIDFromCoOrdinate(x, y));
                    boardPanel.boardTiles.get(boardDirection.traverseTileID(tileCoOrd)).setStyle(boardPanel.boardTiles.get(tileCoOrd).originalColorString);
                });

                pieceImage.setOnMouseDragged(e -> {
                    if(showLegalMoves){
                        sourceTilePanel.setStyle(sourceTilePanel.originalColorString == darkTileColorString ? selectedTileColorDarkString : selectedTileColorLightString);
                    }
                    pieceImage.relocate(
                            Math.max(0,Math.min(TILE_SIZE * WIDTH- TILE_SIZE, e.getSceneX() - pieceImage.mouseX + pieceImage.oldX)),
                            Math.max(0,Math.min(TILE_SIZE * WIDTH- TILE_SIZE, e.getSceneY() - pieceImage.mouseY + pieceImage.oldY)));


                });

                pieceImage.setOnMouseReleased(e -> {

                    int newX = toBoard(pieceImage.getLayoutX());
                    int newY = toBoard(pieceImage.getLayoutY());
                    int x0 = (int) pieceImage.getOldX();
                    int y0 = (int) pieceImage.getOldY();

                    final int tileCoOrd = boardDirection.traverseTileID(tileIDFromCoOrdinate(newX, newY));
                    destinationTile = chessBoard.getTile(tileCoOrd);
                    final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                    final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);

                    if (transition.getMoveStatus().isDone()) {

                        chessBoard = transition.getToBoard();
                        //todo add move to movelog

                        sourceTilePanel.setStyle(sourceTilePanel.originalColorString);
                        sourceTilePanel = null;
                        sourceTile = null;
                        humanMovedPiece = null;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    } else {
                        //null move - relocate to old position
                        pieceImage.relocate(x0, y0);
                        sourceTilePanel = null;
                        sourceTile = null;
                        humanMovedPiece = null;
                        Collection<Move> selectedPiece = pieceLegalMoves(chessBoard);
                        for (final TilePanel tilePanel: boardPanel.boardTiles){
                            tilePanel.assignTileColor();
                        }
                    }
                });
            }
        }
    }

    private int toBoard(double pixel) {
        return (int) ((pixel + TILE_SIZE / 2) / TILE_SIZE);
    }

    private int getCoOrdinateX(int tileID) {
        int x = tileID % 8;
        return x;

    }

    private int getCoOrdinateY(int tileID) {
        int y = (int) Math.floor(tileID / 8);
        return y;
    }

    private MenuBar createTableMenuBar() {
        final MenuBar tableMenuBar = new MenuBar();
        tableMenuBar.getMenus().add(createFileMenu());
        tableMenuBar.getMenus().add(createPreferenceMenu());
        return tableMenuBar;
    }

    private Menu createFileMenu() {
        final Menu fileMenu = new Menu("File");
        final MenuItem openPGN = new MenuItem("Load PGN File");
        openPGN.setOnAction(e -> {
            System.out.println("Open that PGN file");
        });
        final MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            System.exit(0);
        });

        final MenuItem newGame = new MenuItem("New game");
        newGame.setOnAction(e -> {
            this.chessBoard = Board.createStandardBoard();
            boardPanel.drawBoard(chessBoard);
        });
        fileMenu.getItems().addAll(openPGN, exit, newGame);
        return fileMenu;
    }

    private Menu createPreferenceMenu() {
        final Menu preferenceMenu = new Menu("Preference");
        final MenuItem changeUIMenuItem = new MenuItem("Change UI To Swing");
        final MenuItem flipBoardMenu = new MenuItem("Flip Board");
        final Menu colorChooserSubMenu = new Menu("Choose Colors");
        final MenuItem chooseDarkMenuItem = new MenuItem("Choose Dark Tile Color");

        chooseDarkMenuItem.setOnAction(event -> {
/*            final java.awt.Color awtColor = JColorChooser.showDialog(new JFrame(), "Choose Dark Tile Color",null);
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0 ;
            javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);

            System.out.println(FxUtils.toRGBCode(fxColor));
*/

            CustomColorDialog dialog = new CustomColorDialog(primaryStage);
            dialog.show();
            dialog.setOnUse(new Runnable() {
                @Override
                public void run() {
                    Color color = dialog.getCustomColor();
                    StringBuilder colorString = new StringBuilder();
                    colorString.append("-fx-background-color: ");
                    colorString.append(FxUtils.toRGBCode(color));
                    colorString.append(";");
                    darkTileColorString = colorString.toString();
                    System.out.println(colorString.toString());
                    boardPanel.drawBoard(chessBoard);

                }
            });

        });


        colorChooserSubMenu.getItems().add(chooseDarkMenuItem);



        final MenuItem chooseLightMenuItem = new MenuItem("Choose Light Tile Color");
        colorChooserSubMenu.getItems().add(chooseLightMenuItem);

        chooseLightMenuItem.setOnAction(event -> {
            CustomColorDialog dialog = new CustomColorDialog(primaryStage);
            dialog.show();
            dialog.setOnUse(new Runnable() {
                @Override
                public void run() {
                    Color color = dialog.getCustomColor();
                    StringBuilder colorString = new StringBuilder();
                    colorString.append("-fx-background-color: ");
                    colorString.append(FxUtils.toRGBCode(color));
                    colorString.append(";");
                    lightTileColorString = colorString.toString();
                    System.out.println(colorString.toString());
                    boardPanel.drawBoard(chessBoard);
                }
            });
        });

        final MenuItem chooseLegalHighlightMenuItem = new MenuItem(
                "Choose Legal Move Highlight Color");
        colorChooserSubMenu.getItems().add(chooseLegalHighlightMenuItem);

        chooseLegalHighlightMenuItem.setOnAction(event -> {

            CustomColorDialog dialog = new CustomColorDialog(primaryStage);
            dialog.show();
            dialog.setOnUse(new Runnable() {
                @Override
                public void run() {
                    Color color = dialog.getCustomColor();
                    StringBuilder colorString = new StringBuilder();
                    colorString.append("-fx-background-color: ");
                    colorString.append(FxUtils.toRGBCode((color.darker())));
                    colorString.append(";");
                    selectedTileColorLightString = colorString.toString();

                    //Getting darker
                    colorString = new StringBuilder();
                    colorString.append("-fx-background-color: ");
                    colorString.append(FxUtils.toRGBCode(color));
                    colorString.append(";");
                    selectedTileColorDarkString = colorString.toString();


                    System.out.println(colorString.toString());
                    boardPanel.drawBoard(chessBoard);
                }
            });
        });



        final CheckMenuItem showLegalMoves = new CheckMenuItem("Show Legal Moves");
        showLegalMoves.setSelected(true);


        flipBoardMenu.setOnAction(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });

        showLegalMoves.setOnAction(e -> {
            if (showLegalMoves.isSelected()){
                this.showLegalMoves = true;
            }
            else{
                this.showLegalMoves = false;
            }
        });

        changeUIMenuItem.setOnAction(event -> {
            //TODO make Changing UI more than once possible
            primaryStage.close();
            Table.get().show();
            Table.get().getBoardPanel().drawBoard(chessBoard);
        });
        preferenceMenu.getItems().addAll(flipBoardMenu, showLegalMoves, colorChooserSubMenu, changeUIMenuItem);
        return preferenceMenu;
    }



    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            int traverseTileID(final int tileID) {
                return tileID;
            }

            @Override
            BoardDirection opposite() {

                System.out.println("Board Flipped");
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            int traverseTileID(final int tileID) {

                return (63 - tileID);
            }

            @Override
            BoardDirection opposite() {
                System.out.println("Board Normal");
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        abstract int traverseTileID (final int tileID);

        abstract BoardDirection opposite();
    }

    public static class FxUtils
    {
        public static String toRGBCode( Color color )
        {
            return String.format( "#%02X%02X%02X",
                    (int)( color.getRed() * 255 ),
                    (int)( color.getGreen() * 255 ),
                    (int)( color.getBlue() * 255 ) );
        }
    }
}
