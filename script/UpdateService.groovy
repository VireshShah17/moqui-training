import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

// Getting the context
ExecutionContext ec = context.ec

// Fetching the data
def record = ec.entity.find("moqui.training.MoquiTraining")

// Applying filters to our record
if (context.trainingID){
    record.condition("trainingID", context.trainingID)
} else if (context.trainingName){
    record.condition("trainingName", context.trainingName)
}

// Fetching a single
record = record.one()

// Updating the record
if (record){
    record.set("trainingDate", context.trainingDate)
    record.set("trainingPrice", context.trainingPrice)
    record.set("trainingDuration", context.trainingDuration)
    record.update()
    context.trainingID = record.trainingID

//    Creating a record if it doesn"t exist
} else{
    def newTrainingID = ec.entity.sequenceIdPrimary("moqui.training.MoquiTraining", null, null)
    EntityValue newRecord = ec.entity.makeValue("moqui.training.MoquiTraining")
    newRecord.set("trainingID", newTrainingID)
    newRecord.set("trainingName", context.trainingName)
    newRecord.set("trainingDate", context.trainingDate)
    newRecord.set("trainingPrice", context.trainingPrice)
    newRecord.set("trainingDuration", context.trainingDuration)
    newRecord.create()
    context.trainingID = newTrainingID



}