<?php


class Recipe implements JsonSerializable
{
    public $id;
    public $name;
    public $author;
    public $type;
    public $description;

    /**
     * Recipe constructor.
     * @param $id
     * @param $name
     * @param $author
     * @param $type
     * @param $description
     */
    public function __construct($id, $name, $type, $author, $description)
    {
        $this->id = $id;
        $this->name = $name;
        $this->type = $type;
        $this->author = $author;
        $this->description = $description;
    }


    public function jsonSerialize()
    {
        return get_object_vars($this);
    }
}