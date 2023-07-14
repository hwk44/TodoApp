import './App.css';
import Todo from "./Todo";
import React, {useEffect, useState} from "react";
import {Container, List, Paper} from "@mui/material";
import Addtodo from './AddTodo';
import {call} from "./service/ApiService"


function App() {
  const [items, setItems] = useState([]); 
  // todo 객체 (item) 을 담을 배열

useEffect (()=>{
  call("/todo", "GET", null)
  .then((response) => setItems(response.data));
},[]);

    const addItem = (item) => {
      // item.id = "ID-" +items.length; // key를 위한 id
      // item.done = false; // done 초기화
      // 업데이트는 반드시 setItems로 하고 새 배열을 만든다.
      // setItems([...items, item]);
      // console.log("items : ", items);
      
      call("/todo","POST", item)
      .then((response) => setItems(response.data));
    };

    const deleteItem = (item) =>{
      // 삭제할 아이템 빼고 다시 저장
      // const newItems = items.filter(e => e.id !== item.id);
      // setItems([...newItems]);
      
      call("/todo","DELETE", item)
      .then((response) => setItems(response.data));
    };

    // item 수정 함수
    // const editItem = () => {
    //   setItems([...items]);
    // };

    const editItem = (item) => {
      call("/todo", "PUT", item)
      .then((response) => setItems(response.data));
    };
    
    // item 만드는 부분
    let todoItems = items.length > 0 && (
      <Paper style={{margin : 16}}>
        <List>
          {items.map((item) => (
            <Todo item={item} key={item.id} editItem={editItem} deleteItem ={deleteItem}/>
          ))}
        </List>
      </Paper>
    );
    
  return (
    <div className="App">
      <Container maxWidth="md">
        <h1>My Todo Application</h1>
        <Addtodo addItem={addItem} />
        <div className='TodoList'>{todoItems}</div>
      </Container>
      </div>
  );
}

export default App;
