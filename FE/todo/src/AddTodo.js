import React, {useState} from "react";

import { Button,Grid, TextField } from "@mui/material";

const Addtodo = (props) => {
    // 사용자 입력을 저장할 객체
    const [item, setItem] = useState({ title : ""});
    // addItem 내려받기
    const addItem = props.addItem;
    
    // onButtonClick 함수
     const onButtonClick = () => {
        addItem(item);
        setItem({title : ""});
    }
    // enterKeyEventHandler
    const enterKeyEventHandler = (e) => {
        if(e.key === "Enter") {
            console.log('You must have pressed Enter ')
            onButtonClick();
        }
    }

    // onInputChange 함수
    const onInputChange = (e) => {
        setItem({title : e.target.value});
        // console.log(item);
    }
    return(
        <Grid container style={{marginTop : 40}} >
            <Grid xs={11} md={11} item style={{paddingRight : 16}}>
                <TextField placeholder="Add Todo here" fullWidth 
                onChange={onInputChange} value={item.title}
                onKeyDown={enterKeyEventHandler}/>
            </Grid>

            <Grid xs={1} md={1} item>
                <Button fullWidth style={{height : '100%'}}
                 color="secondary" variant="outlined" 
                 onClick={onButtonClick} >
                + </Button>
            </Grid>
        </Grid>
    );
};
export default Addtodo;