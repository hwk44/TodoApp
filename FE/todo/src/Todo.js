import React, {useState} from "react";
import {ListItem, ListItemText, InputBase, Checkbox, // create 기능만 구현할때
        ListItemSecondaryAction, IconButton} from "@mui/material"; // delete 로직 추가를 위한
import DeleteOutlinedIcon from '@mui/icons-material/DeleteOutlined';

const Todo = (props) => {

    const [item, setItem] = useState(props.item);
    const deleteItem = props.deleteItem;
    const [readOnly, setReadOnly] = useState(true);
    const editItem = props.editItem;

    // deleteEventHandler
    const deleteEventHandler = () => {
        deleteItem(item);
    };
    
    // turnOffReadOnly todo title 클릭 시에 readOnly false
    const turnOffReadOnly = () => {
        setReadOnly(false);
    };

    // turnOnReadOnly local
    // const turnOnReadOnly = (e) => {
    //     if(e.key ==="Enter") setReadOnly(true);
    // };

    const turnOnReadOnly = (e) => {
        if(e.key ==="Enter" && readOnly === false){
            setReadOnly(true);
            editItem(item);
        } 
    };

    // editEventHandler local
    // const editEventHandler =(e) => {
    //     item.title = e.target.value;
    //     editItem();
    // };

    const editEventHandler = (e) => {
        setItem({...item, title: e.target.value});
    };

    // checkboxEventHandler local
    // const checkboxEventHandler = (e) => {
    //     item.done = e.target.checked;
    //     editItem();
    // };

    const checkboxEventHandler = (e) => {
        item.done = e.target.checked;
        editItem(item);
    };

    return (
        <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler} />
            <ListItemText>
            <InputBase
                inputProps={{"aria-label" : "naked", readOnly : readOnly}}
                onClick={turnOffReadOnly}
                onKeyDown={turnOnReadOnly}
                onChange={editEventHandler}
                type="text"
                id={item.id}
                name={item.id}
                value={item.title}
                multiline={true}
                fullWidth={true} />
            </ListItemText>

            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutlinedIcon />
                </IconButton>
            
            </ListItemSecondaryAction>
        </ListItem>
    );    

};

export default Todo;