package Main.Guide;

import Main.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Guide {

    /**
     * ID of guide (auto-increments)
     */
    private int ID;
    /**
     * Title of the guide (for quick reference while searching)
     */
    private SimpleStringProperty Title;
    /**
     * Description of the guide (contains software required, supported vehicles etc.)
     */
    private SimpleStringProperty Description;
    /**
     * Path to the guide (html, pdf, txt etc)
     */
    private SimpleStringProperty Path;
    /**
     * Type of guide (programming, diagnostics, misc etc.)
     */
    private int Type;    //will change

    public Guide(String title, String description, String path) {
        this.Title = new SimpleStringProperty(title);
        this.Description = new SimpleStringProperty(description);
        this.Path = new SimpleStringProperty(path);
    }

    /**
     * Get all guides from database
     * @return Guides from database
     */
    public static ArrayList<Guide> getGuides()
    {
        try
        {
            ResultSet dbGuides = DatabaseManager.get("SELECT * FROM Guides");
            if (dbGuides != null) {
                ArrayList<Guide> guides = new ArrayList<>();
                while (dbGuides.next())
                {
                    Guide guide = new Guide(dbGuides.getString("Title"),
                            dbGuides.getString("Description"),
                            dbGuides.getString("Path"));
                    guide.ID = dbGuides.getInt("ID");
                    guide.Type = dbGuides.getInt("Type");
                    guides.add(guide);
                }
                return guides;
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    public void openInDefaultProgram() throws IOException {
        File file = new File(this.getPath());
        Desktop.getDesktop().open(file);
    }


    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getTitle()
    {
        return Title.get();
    }

    public void setTitle(String title)
    {
        this.Title.set(title);
    }

    public String getDescription()
    {
        return Description.get();
    }

    public void setDescription(String description)
    {
        this.Description.set(description);
    }

    public int getType()
    {
        return Type;
    }

    public void setType(int type)
    {
        Type = type;
    }

    public String getPath()
    {
        return Path.get();
    }

    public void setPath(String path)
    {
        this.Path.set(path);
    }
}
