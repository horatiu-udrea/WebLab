namespace lab9.Models
{
    public class Recipe
    {
        public int id;
        public string name;
        public string type;
        public string author;
        public string description;

        public Recipe(int id, string name, string type, string author, string description)
        {
            this.id = id;
            this.name = name;
            this.author = author;
            this.type = type;
            this.description = description;
        }
    }
}