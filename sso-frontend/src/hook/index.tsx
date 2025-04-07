import { useForm } from "@mantine/form"
import { useLazyLoginQuery } from "../api"
import { useNavigate, useSearchParams } from "react-router-dom"

export type SSOFormProps = {
    email: string
    password: string
}

export const useSSOForm = () => {
    const form = useForm<SSOFormProps>()
    const [trigger] = useLazyLoginQuery()
    const [params, ] = useSearchParams()
    const navigate = useNavigate()
    const handleSubmit = async (vals: SSOFormProps) => {
        try{
            const redirectURI = params.get('redirectURI')
            const appId = params.get('appId')
            if(redirectURI == null || appId == null){
                throw ''
            }
            if(appId === 'client' || appId === 'employee'){
                await trigger(vals).unwrap().then(data => {
                    localStorage.setItem('token', data.token)
                    navigate(`/apps?redirectURI=${redirectURI}&appId=${appId}`)
                    //window.location.href = `${redirectURI}?token=${data.token}`
                })
            }
            else{
                throw ''
            }
        }
        catch {
            alert('Не удалось войти')
        }
    }
    return {
        form: form,
        onSubmit: form.onSubmit(handleSubmit)
    }

}

export {useApps} from './useApps'