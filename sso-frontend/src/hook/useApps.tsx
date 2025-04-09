import { useSearchParams } from "react-router-dom"
import { useAuthowireMutation, useMyAppsQuery } from "../api"
import { Button, Container, Title } from "@mantine/core"

export const useApps  = () =>{ 
    const [params, ] = useSearchParams()
    const redirectURI = params.get('redirectURI')
    const appId = params.get('appId')
    const {data} = useMyAppsQuery()
    const [trigger] = useAuthowireMutation()
    console.log(data)
    const isApplicationAuthowired = data?.filter(app => app.appId === appId).length === 1

    const AuthowiredComponent = () => {
        if(!isApplicationAuthowired)
        {
            return (
                <Container>
                    <Title>{`Приложение ${appId} получит доступ к вашим данным`}</Title>
                    <Button onClick={() => {
                        if(appId !== null){
                            trigger({appId}).unwrap().then(()=>{
                                location.href = `${redirectURI}?code=${localStorage.getItem("token")!}`!
                            }).catch(()=>{
                                alert('Не удалось авторизовать сервис')
                            })
                        }
                    }}>Подтверждаю</Button>
                    <Button color='red' onClick={()=> {
                        location.href=`${redirectURI}?code=${localStorage.getItem("token")!}`!
                        }}>Отказаться</Button>
                </Container>
            )
        }
        else {
            location.href=`${redirectURI}?code=${localStorage.getItem("token")!}`!
        }
    }
    return {
        isApplicationAuthowired, 
        AuthowiredComponent
    }
}