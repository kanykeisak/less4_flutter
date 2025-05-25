import 'package:equatable/equatable.dart';
import '../models/dog_image.dart';

abstract class DogState extends Equatable {
  const DogState();

  @override
  List<Object?> get props => [];
}

class DogInitial extends DogState {}

class DogLoading extends DogState {}

class DogLoaded extends DogState {
  final DogImage dogImage;

  const DogLoaded(this.dogImage);

  @override
  List<Object?> get props => [dogImage];
}

class DogError extends DogState {
  final String message;

  const DogError(this.message);

  @override
  List<Object?> get props => [message];
} 