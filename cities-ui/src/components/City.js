import './City.css'
import React, {useState} from "react";
import axios from "axios";

function City(props) {

    const [updatedName, setUpdatedName] = useState(props.name)
    const [updatedPhoto, setUpdatedPhoto] = useState(props.photo)

    const onClickUpdateHandler = event => {
        event.preventDefault()
        axios.put(`${props.baseUrl}/city`, {
            id: props.cityId,
            name: updatedName,
            photo: updatedPhoto
        }).then(response => {
            if (response.status === 204) {
                props.onCityUpdate({
                    id: props.cityId,
                    name: updatedName,
                    photo: updatedPhoto
                })
            }

        }).catch(error => {
            console.log(`Error while updating: ${error}`)
        })
    }

    const updateCityHandler = event => {
        setUpdatedName(event.target.value)
    };

    const updatePhotoHandler = event => {
        setUpdatedPhoto(event.target.value)
    };

    return (
        <div className="city-box">
            <img className="city" src={props.photo} alt="Image did not load" />
            <label className="city-name">{props.name}</label>
            <form onSubmit={onClickUpdateHandler}>
                <input className="city top" placeholder="Edit city name" value={updatedName} onInput={updateCityHandler}/>
                <input className="city bottom" placeholder="Edit city picture" value={updatedPhoto} onInput={updatePhotoHandler}/>
                <button type="submit" className="city" onClick={onClickUpdateHandler}> Update </button>
            </form>
        </div>
    )
}
export default City;