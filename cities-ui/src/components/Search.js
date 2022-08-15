import './Search.css'
import {useState} from "react";

function Search(props) {

    const [inputName, setInputName] = useState('')

    const onInputChange = event => {
        setInputName(event.target.value);
    }

    const onClickHanlder = () => {
        props.searchByName(inputName)
    }

    return (
        <div className="search-box">
            <input className="search-input" placeholder="Search cities by name" value={inputName} onInput={onInputChange}/>
            <button className="search-button" onClick={onClickHanlder}> Search </button>
        </div>
    )
}

export default Search;