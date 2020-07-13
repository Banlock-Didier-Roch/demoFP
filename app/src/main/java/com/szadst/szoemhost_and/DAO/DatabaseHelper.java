package com.szadst.szoemhost_and.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.szadst.szoemhost_and.Domaine.Eleves;
import com.szadst.szoemhost_and.Domaine.Parent;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SEATS.db";
    public static final String TABLE_NAME1 = "table_eleve";
    public static final String TABLE_NAME2 = "table_utilisateur";
    public static final String TABLE_NAME3 = "table_parent";
    public static final String TABLE_NAME4 = "table_classe";
    public static final String TABLE_NAME5 = "table_notification";
    public static final String TABLE_NAME6 = "table_parent_has_notification";
    public static final String TABLE_NAME7 = "table_emargement";
    public static final String TABLE_NAME8 = "table_eleve_has_parent";

    public static final String COL2_Eleve = "NOM";
    public static final String COL1_Eleve = "ID";
    public static final String COL3_Eleve = "PRENOM";
    public static final String COL4_Eleve = "CLASSE";
    public static final String COL5_Eleve = "MATRICULE";
    public static final String COL6_Eleve = "EMPREINTE";

    public static final String COL1_Utilisateur = "ID";
    public static final String COL2_Utilisateur = "LOGIN";
    public static final String COL3_Utilisateur = "PASSWORD";

    public static final String COL1_Parent = "ID";
    public static final String COL2_Parent = "NOM";
    public static final String COL3_Parent = "PRENOM";
    public static final String COL4_Parent = "typeNotif";
    public static final String COL5_Parent = "telParent";
    public static final String COL6_Parent = "emailParent";

    public static final String COL1_Notification = "ID";
    public static final String COL2_Notification = "DATE";
    public static final String COL3_Notification = "MESSAGE";

    public static final String COL1_eleve_has_parent = "ID_PARENT";
    public static final String COL2_eleve_has_parent = "ID_ELEVE";
    public static final String COL3_eleve_has_parent = "ID_NOTIF";

    public static final String COL1_Classe = "nomClasse";

    public static final String COL1_Parent_Has_Notification = "ID_NOTIF";
    public static final String COL2_Parent_Has_Notification = "ID_PARENT";

    public static final String COL1_Emargement = "ID";
    public static final String COL2_Emargement = "ID_ELEVE";
    public static final String COL3_Emargement = "HEURE";
    public static final String COL4_Emargement = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME1 + "("
                + COL1_Eleve + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2_Eleve + " VARCHAR(45),"
                + COL3_Eleve + " VARCHAR(45),"
                + COL4_Eleve + " VARCHAR(45),"
                + COL5_Eleve + " VARCHAR(45)" +
                ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

/*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME7);

        db.execSQL("create table " + TABLE_NAME2 + "("
                + COL1_Utilisateur + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2_Utilisateur + " VARCHAR(45),"
                + COL3_Utilisateur + " VARCHAR(45));"
        );

        db.execSQL("CREATE TABLE table_classe(\n" +
                "  nomClasse VARCHAR(45) PRIMARY KEY);"
        );

        db.execSQL("create table  table_eleve (ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                NOM VARCHAR(45),\n" +
                "                PRENOM VARCHAR(45),\n" +
                "                CLASSE VARCHAR(45),\n" +
                "                MATRICULE VARCHAR(45),\n" +
                "                EMPREINTE VARCHAR(45),\n" +
                "   FOREIGN KEY (`CLASSE`) REFERENCES `table_classe` (`NOM`)\n" +
                ");"
        );

        db.execSQL("create table  table_parent (ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                NOM VARCHAR(45),\n" +
                "                PRENOM VARCHAR(45),\n" +
                "                typeNotif VARCHAR(45),\n" +
                "                telParent VARCHAR(45),\n" +
                "                emailParent VARCHAR(45)\n" +
                ");"
        );

        db.execSQL("create table  table_eleve_has_parent (ID_NOTIF INTEGER,\n" +
                        "                ID_PARENT INTEGER,\n" +
                        "                ID_ELEVE INTEGER,\n" +
                        "                FOREIGN KEY (`ID_PARENT`) REFERENCES `table_parent` (`ID`),\n" +
                        "                FOREIGN KEY (`ID_ELEVE`) REFERENCES `table_eleve` (`ID`),\n" +
                        "                PRIMARY KEY (`ID_PARENT`,`ID_ELEVE`)\n" +
                        ");"
                );

        db.execSQL("create table  table_notification (ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                DATE DATETIME,\n" +
                "                MESSAGE VARCHAR(500)\n" +
                ");"
        );

        db.execSQL("create table  table_parent_has_notification (ID_NOTIF INTEGER,\n" +
                "                ID_PARENT INTEGER,\n" +
                "                FOREIGN KEY (`ID_NOTIF`) REFERENCES `table_notification` (`ID`),\n" +
                "                FOREIGN KEY (`ID_PARENT`) REFERENCES `table_parent` (`ID`),\n" +
                "                PRIMARY KEY (`ID_NOTIF`,`ID_PARENT`)\n" +
                ");"
        );

        db.execSQL("create table  table_emargement (ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                ID_ELEVE INTEGER,\n" +
                "                HEURE TIME,\n" +
                "                DATE DATE,\n" +
                "                FOREIGN KEY (`ID_ELEVE`) REFERENCES `table_eleve` (`ID`)\n" +
                ");"
        );
*/
    }

    public boolean insertEleve(String nom, String prenom, String classe, String matricule, Parent parent, int fpId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();

        //values of 'eleve'
        contentValues.put(COL2_Eleve, nom);
        contentValues.put(COL3_Eleve, prenom);
        contentValues.put(COL4_Eleve, classe);
        contentValues.put(COL5_Eleve, matricule);
        contentValues.put(COL6_Eleve, fpId+"");


        //insert 'eleve'
        long result = db.insert(TABLE_NAME1, null, contentValues);

        //values of 'eleve_has_parent'
        contentValues2.put(COL1_eleve_has_parent, parent.getId());
        contentValues2.put(COL2_eleve_has_parent, result);
        if(parent.getTypeNotif().equalsIgnoreCase("Journalier"))
            contentValues2.put(COL3_eleve_has_parent, 1);
        else if(parent.getTypeNotif().equalsIgnoreCase("Hebdomadaire"))
            contentValues2.put(COL3_eleve_has_parent, 2);
        else if(parent.getTypeNotif().equalsIgnoreCase("Mensuel"))
            contentValues2.put(COL3_eleve_has_parent, 3);


        //insert 'eleve_has_parent'
        long result2 = db.insert(TABLE_NAME8, null, contentValues2);


        if (result == -1 || result2 ==-1)
            return false;
        else
            return true;
    }

    public boolean insertParent(String nom, String prenom, String typeNotif, String telephone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_Parent, nom);
        contentValues.put(COL3_Parent, prenom);
        contentValues.put(COL4_Parent, typeNotif);
        contentValues.put(COL5_Parent, telephone);
        contentValues.put(COL6_Parent, email);

        long result = db.insert(TABLE_NAME3, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertUtilisateur(String login, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_Utilisateur, login);
        contentValues.put(COL3_Utilisateur, pwd);

        long result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertClasse(String classe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_Classe, classe);

        long result = db.insert(TABLE_NAME4, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateEleve(int id, String nom, String prenom, String classe, String matricule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_Eleve, nom);
        contentValues.put(COL3_Eleve, prenom);
        contentValues.put(COL4_Eleve, classe);
        contentValues.put(COL5_Eleve, matricule);

        long result = db.update(TABLE_NAME1, contentValues,COL1_Eleve+"="+id,null);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateParent(int id, String nom, String prenom, String typeNotif, String telephone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_Parent, nom);
        contentValues.put(COL3_Parent, prenom);
        contentValues.put(COL4_Parent, typeNotif);
        contentValues.put(COL5_Parent, telephone);
        contentValues.put(COL6_Parent, email);

        long result = db.update(TABLE_NAME3, contentValues,COL1_Parent+"="+id,null);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        return result;
    }

    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME4, null);
        return result;
    }

    public Cursor getAllParents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return result;
    }

    public Parent getParent(Eleves eleve){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT t.ID,t.NOM,t.PRENOM,t.typeNotif,t.telParent,t.emailParent \n" +
                "FROM table_parent t, table_eleve t1, table_eleve_has_parent t2\n" +
                "WHERE  t.ID = t2.ID_PARENT\n" +
                "AND t1.ID = t2.ID_ELEVE\n" +
                "AND t1.ID = "+eleve.getId(), null);
        if(result.moveToNext())
            return new Parent(result.getInt(0),result.getString(1), result.getString(2), result.getString(5), result.getString(3),result.getString(4));
        else
            return null;
    }
}
