const paymentStart= () =>{
	console.log("payment started!");
    let amount = $("#payment_field").val();
    console.log(amount);

    if(amount == '' || amount == null){
     	//alert('amount needed!');
        swal("Failed!!","Amount required!","error");
        return ;
    }

    $.ajax({
        url:'/create_order',
        data:JSON.stringify({amount:amount,info:'order_request'}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            //invoked when success occurs
            console.log(response);

            if(response.status == "created"){
               // open paymement form
               console.log("created order");
               let options={
                key:"rzp_test_3WdFkgfwuWTfRd",
                amount:response.amount,
                currency:"INR",
                name:"Food Order App",
                description:"Order Recieved",
                image:"https://cdn.pixabay.com/photo/2018/01/12/16/31/nougat-3078581_960_720.jpg",
                order_id:response.id,
                handler:function(response){



                    console.log(response.razorpay_payment_id);
                    console.log(response.razorpay_order_id);
                    console.log(response.razorpay_signature);
                    
                    swal("Good Job!","Paid Successfully!!","success");
                },
                prefill:{
                    name:"",
                    email:"",
                    contact:""
                },
                notes:{
                    address:"My Food Order App - miniproject!"
                },
                theme:{
                    color:"#3399cc"
                }
               };

               let rzp = new Razorpay(options);
               //handler if the call fails
               rzp.on("payment.failed",function(response){
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);
                
                swal("Payment-Failed","Oops - something went wrong","error");
               });
               
               rzp.open(); 

            }

        }
        ,error:function(error){
            //invoked when error.
            console.log(error);
            alert('Something went wrong');
        }
    });
};