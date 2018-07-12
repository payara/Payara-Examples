package fish.payara.team.info.models;

import java.io.Serializable;

/**
 * @author Fraser Savage
 */
public class MemberList implements Serializable {
    private String name;
    private String listOutput;
    private String listBody;

    public String getListBody() {
        return listBody;
    }

    public void setListBody(String listBody) {
        this.listBody = listBody;
    }

    /**
     * Wraps the list body in a paragraph with some explanatory text.
     * @return - Returns the resultant string.
     */
    public String getListOutput() {
        listOutput = "<p>The following team members have that name:<br>\n" +
                listBody +
                "<br> Please enter the ID of the team member you would like to manage.</p> \n";
        return listOutput;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
