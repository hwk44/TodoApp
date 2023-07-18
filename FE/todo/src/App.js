import './App.css';
import Todo from "./Todo";
import React, {useEffect, useState} from "react";
import {Container, List, Paper,Grid,
  Button,
  AppBar,
  Toolbar,
  Typography,} from "@mui/material";
import Addtodo from './AddTodo';
import Loading from './Loading';
import {call, signout} from "./service/ApiService"


function App() {
  // todo 객체 (item) 을 담을 배열
  const [items, setItems] = useState([]); 
  
  // 로딩중
  const [loading, setLoading] = useState(true);

useEffect (()=>{
  call("/todo", "GET", null)
  .then((response) => {
  setItems(response.data);
  setLoading(false);
});
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

     // navigationBar 추가
  let navigationBar = (
    <AppBar position="static">
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">My Todo</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );

  /* 로딩중이 아닐 때 렌더링 할 부분 */
  let todoListPage = (
    <div>
      {navigationBar} {/* 네비게이션 바 렌더링 */}
      <Container maxWidth="md">
        <h1>My Todo Application</h1>
        <Addtodo addItem={addItem} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );

  /* 로딩중일 때 렌더링 할 부분 */
  let loadingPage = <Loading />;
  let content = loadingPage;

  if (!loading) {
    /* 로딩중이 아니면 todoListPage를 선택*/
    content = todoListPage;
  }


  /* 선택한 content 렌더링 */
  return <div className="App">{content}</div>;
}


export default App;
